package com.points.customerrewards.service.impl;

import com.points.customerrewards.entity.Transaction;
import com.points.customerrewards.exception.NoTransactionFoundException;
import com.points.customerrewards.model.PointsResponse;
import com.points.customerrewards.model.TransactionRequest;
import com.points.customerrewards.repository.TransactionRepository;
import com.points.customerrewards.service.CustomerRewardsService;
import com.points.customerrewards.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerRewardsServiceImpl implements CustomerRewardsService {

    @Autowired
    private TransactionRepository repository;

    @Override
    public Transaction add(TransactionRequest request) {
        return repository.save(transactionMapper.apply(request));
    }

    private final Function<TransactionRequest,Transaction> transactionMapper = request -> Transaction.builder()
            .productName(request.getProductName())
            .amount(request.getAmount())
            .customerId(request.getCustomerId())
            .date(new Date())
            .build();

    @Override
    public PointsResponse getRewards(Long customerId) throws NoTransactionFoundException {
        List<Transaction> transactions = repository.findByCustomerIdAndDateGreaterThanEqual(customerId,DateUtil.getOldStartingDate(-3));
        if(transactions.isEmpty()){
            throw new NoTransactionFoundException("No transaction present for the customer Id "+customerId);
        }
        Map<String, Double> rewards = getRewardsMap(transactions);

        return PointsResponse.builder()
                .customerId(customerId)
                .totalRewards(rewards.values().stream().reduce(0.0,Double::sum))
                .rewards(rewards)
                .build();
    }

    private Map<String, Double> getRewardsMap(List<Transaction> transactions) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
        return transactions.stream()
                .collect(Collectors.toMap(transaction -> sdf.format(transaction.getDate()), this::getRewardPerOrder,Double::sum));
    }

    @Override
    public List<PointsResponse> getAllRewards() throws NoTransactionFoundException {
        List<Transaction> transactions = repository.findByDateGreaterThanEqual(DateUtil.getOldStartingDate(-3));
        if(transactions.isEmpty()){
            throw new NoTransactionFoundException("No transaction present");
        }
        Map<Long, List<Transaction>> userTransactions = transactions.stream().collect(Collectors.groupingBy(Transaction::getCustomerId));

        return userTransactions.entrySet().stream().map(entry->{
            Map<String, Double> rewardsMap = getRewardsMap(entry.getValue());
            return PointsResponse.builder()
                    .customerId(entry.getKey())
                    .totalRewards(rewardsMap.values().stream().reduce(0.0,Double::sum))
                    .rewards(rewardsMap)
                    .build();
        }).collect(Collectors.toList());
    }

    private Double getRewardPerOrder(Transaction transaction) {
        Double amount = transaction.getAmount();
        double rewards=0.0;
        if(amount>50){
            double difference = amount-50;
            rewards+=difference*1;
            if(difference>50) {
                rewards += (difference - 50) * 1;
            }
        }
        log.debug("Rewards for amount {} is {}",transaction.getAmount(),rewards);
        return rewards;
    }

    @PostConstruct
    public void mockTransactionAtStartup(){
        List<Transaction> transactions =new ArrayList<>();
        transactions.add(Transaction.builder().customerId(1L).amount(150.0).date(Date.from(LocalDate.of(2022, 7, 15).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(70.0).date(Date.from(LocalDate.of(2022, 7, 18).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(20.0).date(Date.from(LocalDate.of(2022, 7, 21).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(60.0).date(Date.from(LocalDate.of(2022, 7, 30).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(90.0).date(Date.from(LocalDate.of(2022, 8, 4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(100.0).date(Date.from(LocalDate.of(2022, 8, 7).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(120.0).date(Date.from(LocalDate.of(2022, 8, 18).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(60.0).date(Date.from(LocalDate.of(2022, 8, 26).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(90.0).date(Date.from(LocalDate.of(2022, 9, 10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(120.0).date(Date.from(LocalDate.of(2022, 9, 15).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(20.0).date(Date.from(LocalDate.of(2022, 9, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        repository.saveAll(transactions);
    }
}

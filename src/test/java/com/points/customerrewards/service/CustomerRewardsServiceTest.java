package com.points.customerrewards.service;

import com.points.customerrewards.entity.Transaction;
import com.points.customerrewards.exception.NoTransactionFoundException;
import com.points.customerrewards.model.PointsResponse;
import com.points.customerrewards.repository.TransactionRepository;
import com.points.customerrewards.service.impl.CustomerRewardsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class CustomerRewardsServiceTest {

    @InjectMocks
    private CustomerRewardsService rewardService = new CustomerRewardsServiceImpl();

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testForNormalTransactions() throws NoTransactionFoundException {
        Mockito.when(transactionRepository.findByCustomerIdAndDateGreaterThanEqual(any(),any())).thenReturn(mockTransactions());
        PointsResponse rewards = rewardService.getRewards(1L);
        Assertions.assertEquals(500, rewards.getTotalRewards());
    }

    @Test
    public void testTransactionWithNoAmountLessThanFifty() throws NoTransactionFoundException {
        Mockito.when(transactionRepository.findByCustomerIdAndDateGreaterThanEqual(any(),any())).thenReturn(mockNoRewards());
        PointsResponse rewards = rewardService.getRewards(1L);
        Assertions.assertEquals(0, rewards.getTotalRewards());
    }

    public List<Transaction> mockTransactions() {
        List<Transaction> transactions = new ArrayList<>();
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
        return transactions;
    }

    public List<Transaction> mockNoRewards() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(Transaction.builder().customerId(1L).amount(50.0).date(Date.from(LocalDate.of(2022, 8, 26).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(30.0).date(Date.from(LocalDate.of(2022, 9, 10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(40.0).date(Date.from(LocalDate.of(2022, 9, 15).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        transactions.add(Transaction.builder().customerId(1L).amount(20.0).date(Date.from(LocalDate.of(2022, 9, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).productName("Test").build());
        return transactions;
    }

}

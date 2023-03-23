package com.points.customerrewards.controller;

import com.points.customerrewards.entity.Transaction;
import com.points.customerrewards.exception.NoTransactionFoundException;
import com.points.customerrewards.model.TransactionRequest;
import com.points.customerrewards.model.PointsResponse;
import com.points.customerrewards.service.CustomerRewardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class CustomerRewardsController {

    @Autowired
    private CustomerRewardsService service;

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> add(@Valid @RequestBody TransactionRequest transactionRequest){
        return new ResponseEntity<>(service.add(transactionRequest), HttpStatus.OK);
    }

    @GetMapping("/rewards")
    public ResponseEntity<PointsResponse> get(@RequestParam("customerId") Long customerId) throws NoTransactionFoundException {
        return new ResponseEntity<>(service.getRewards(customerId), HttpStatus.OK);
    }

    @GetMapping("/rewards/all")
    public ResponseEntity<List<PointsResponse>> get() throws NoTransactionFoundException {
        return new ResponseEntity<>(service.getAllRewards(), HttpStatus.OK);
    }

}

package com.points.customerrewards.service;


import com.points.customerrewards.entity.Transaction;
import com.points.customerrewards.exception.NoTransactionFoundException;
import com.points.customerrewards.model.TransactionRequest;
import com.points.customerrewards.model.PointsResponse;

import java.util.List;

public interface CustomerRewardsService {

    Transaction add(TransactionRequest transactionRequest);

    PointsResponse getRewards(Long customerId) throws NoTransactionFoundException;

    List<PointsResponse> getAllRewards() throws NoTransactionFoundException;

}

package com.points.customerrewards.repository;

import com.points.customerrewards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCustomerId(Long customerId);

    List<Transaction> findByCustomerIdAndDateGreaterThanEqual(Long customerId, Date date);

    List<Transaction> findByDateGreaterThanEqual(Date date);


}

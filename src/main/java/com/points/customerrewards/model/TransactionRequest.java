package com.points.customerrewards.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @NotNull(message = "Please provide valid customer Id")
    private Long customerId;

    @NotNull(message = "Please provide valid product name")
    private String productName;

    @NotNull(message = "Please provide valid transaction amount")
    @Min(value = 0, message = "Transaction amount needs to be more than zero")
    private Double amount;

}

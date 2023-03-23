package com.points.customerrewards.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsResponse {

    private Long customerId;
    private Map<String, Double> rewards;
    private Double totalRewards;

}

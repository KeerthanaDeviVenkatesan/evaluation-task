package com.springboot.evaluationtask.DashboardModule.Dto;

import com.springboot.evaluationtask.DashboardModule.enity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDTO {
    private String stockSymbol;
    private int quantity;
    private String status;
    private LocalDateTime timestamp;



}


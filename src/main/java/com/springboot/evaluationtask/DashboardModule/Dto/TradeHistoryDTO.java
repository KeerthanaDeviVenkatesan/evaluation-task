package com.springboot.evaluationtask.DashboardModule.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TradeHistoryDTO {

    private Long id;
    private String stockSymbol;
    private String orderType;
    private int quantity;
    private double price;
    private String status;
    private LocalDateTime timestamp;
}

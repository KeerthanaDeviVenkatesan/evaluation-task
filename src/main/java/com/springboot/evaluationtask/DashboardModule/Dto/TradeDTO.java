package com.springboot.evaluationtask.DashboardModule.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TradeDTO {
    private Long userTradeId;
    private String stockSymbol;
    private String orderType;
    private int quantity;
    private double price;
    private LocalDateTime timestamp;
    private String status;

}

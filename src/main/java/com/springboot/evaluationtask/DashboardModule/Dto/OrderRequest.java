package com.springboot.evaluationtask.DashboardModule.Dto;

import lombok.Data;

@Data
public class OrderRequest {
    private String stockSymbol;
    private String orderType;
    private int quantity;
    private double price;

}

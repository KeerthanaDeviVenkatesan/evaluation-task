package com.springboot.evaluationtask.DashboardModule.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
     private String stockSymbol;
     private int quantity;
     private String status;
     private LocalDateTime timestamp;


}

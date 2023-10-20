package com.springboot.evaluationtask.DashboardModule.Dto;

import com.springboot.evaluationtask.DashboardModule.enity.Order;
import com.springboot.evaluationtask.DashboardModule.enity.Symbol;
import com.springboot.evaluationtask.dto.AuthRequest;
import com.springboot.evaluationtask.entity.User;
import lombok.Data;

@Data
public class TradeRequest {
    User user;
    AuthRequest authRequest;
    String searchString;
    Long userId;
    String userName;
    Long groupId;
    String groupName;
    AddSymbol symbol;
    String symbolName;
    Order order;
    String orderId;
}

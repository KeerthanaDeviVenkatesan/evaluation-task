package com.springboot.evaluationtask.DashboardModule.service;

import com.springboot.evaluationtask.DashboardModule.Dto.TradeRequest;
import com.springboot.evaluationtask.DashboardModule.Dto.WatchlistGroupDTO;
import com.springboot.evaluationtask.DashboardModule.exception.ArgumentConstraintViolation;
import com.springboot.evaluationtask.dto.UserDTO;
import com.springboot.evaluationtask.entity.User;

import java.util.List;

public interface DashBoardService {
    String addWatchlistGroup(TradeRequest tradeRequest);
    List<WatchlistGroupDTO> getWatchlistGroups();
    UserDTO addSymbolToWatchlistGroups(TradeRequest tradeRequest) throws ArgumentConstraintViolation;
    List<String> getSymbolsFromWatchlistGroups(TradeRequest tradeRequest);
     String addOrder(TradeRequest tradeRequest);
    String cancelOrder(TradeRequest tradeRequest);


}

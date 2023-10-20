package com.springboot.evaluationtask.DashboardModule.controller;


import com.springboot.evaluationtask.DashboardModule.Dto.*;
import com.springboot.evaluationtask.DashboardModule.enity.Symbol;
import com.springboot.evaluationtask.DashboardModule.enity.Trade;
import com.springboot.evaluationtask.DashboardModule.exception.ArgumentConstraintViolation;
import com.springboot.evaluationtask.DashboardModule.service.Impl.DashBoardServiceImpl;
import com.springboot.evaluationtask.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {
    @Autowired
    DashBoardServiceImpl dashBoardService;

    @PostMapping("/addGroups")        //add watchlist group to the users watchlist
    public ResponseEntity<String> addWatchlistGroup(@RequestBody TradeRequest tradeRequest)
    {
        String addWatchlistGroup =  dashBoardService.addWatchlistGroup(tradeRequest);
        return ResponseEntity.ok("added"+addWatchlistGroup);
    }

    @GetMapping("/groups")         //get all the users watchlist groups
    public List<WatchlistGroupDTO> getWatchlistGroups() {
        List<WatchlistGroupDTO> watchlistGroups = dashBoardService.getWatchlistGroups();
        return ResponseEntity.ok(watchlistGroups).getBody();
    }
    //add symbols to the database
    @PostMapping("/createSymbol")
    public ResponseEntity<Symbol> createSymbol(@RequestBody Symbol symbol) {
        Symbol createdSymbol = dashBoardService.createSymbol(symbol);
        return new ResponseEntity<>(createdSymbol, HttpStatus.CREATED);
    }
    @PostMapping("/addSymbols")        //Add symbols to user's watchlist group
    public  ResponseEntity<String>  addSymbol(@RequestBody TradeRequest tradeRequest) throws ArgumentConstraintViolation {
        UserDTO user = dashBoardService.addSymbolToWatchlistGroups(tradeRequest);
        return ResponseEntity.ok("symbolAdded"+user);
    }
    @GetMapping("/getSymbol")     //get all the symbols of a user's watchlist group based on group ID
    public ResponseEntity<String> getSymbols(@RequestBody TradeRequest tradeRequest) {
        List<String> symbols = dashBoardService.getSymbolsFromWatchlistGroups(tradeRequest);
        return ResponseEntity.ok("symbols"+symbols);
    }
    //add order

    @PostMapping("/addOrder")
    public ResponseEntity<String> addOrder(@RequestBody TradeRequest tradeRequest)
    {
        String response = dashBoardService.addOrder(tradeRequest);
        return ResponseEntity.ok(response);
    }
    //cancel order

    @PostMapping("/cancelOrder")   // Adding order
    public ResponseEntity<String> cancelOrder(@RequestBody TradeRequest tradeRequest)
    {
        String response = dashBoardService.cancelOrder(tradeRequest);
        return ResponseEntity.ok(response);
    }


//add portfolio
    @GetMapping("/port")
    public List<PortfolioDTO> getPortfolio() {
        return dashBoardService.getPortfolio();
    }
//tradeHistory
    @GetMapping("/tradeHistory")
    public List<TradeHistoryDTO> getTradeHistory() {
        return dashBoardService.getTradeHistory();
}



}

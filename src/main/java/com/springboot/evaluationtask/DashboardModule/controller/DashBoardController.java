package com.springboot.evaluationtask.DashboardModule.controller;

import com.springboot.evaluationtask.DashboardModule.Dto.*;
import com.springboot.evaluationtask.DashboardModule.enity.Symbol;
import com.springboot.evaluationtask.DashboardModule.exception.ArgumentConstraintViolation;
import com.springboot.evaluationtask.DashboardModule.service.Impl.DashBoardServiceImpl;
import com.springboot.evaluationtask.dto.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {
    private static final Logger logger = LogManager.getLogger(DashBoardController.class);

    @Autowired
    DashBoardServiceImpl dashBoardService;

    @PostMapping("/addGroups")
    public ResponseEntity<String> addWatchlistGroup(@RequestBody TradeRequest tradeRequest) {
        logger.info("Adding a watchlist group: {}", tradeRequest);
        String addWatchlistGroup = dashBoardService.addWatchlistGroup(tradeRequest);
        logger.info("Added watchlist group: {}", addWatchlistGroup);
        return ResponseEntity.ok("added" + addWatchlistGroup);
    }

    @GetMapping("/groups")
    public List<WatchlistGroupDTO> getWatchlistGroups() {
        logger.info("Getting all watchlist groups");
        List<WatchlistGroupDTO> watchlistGroups = dashBoardService.getWatchlistGroups();
        logger.info("Retrieved {} watchlist groups", watchlistGroups.size());
        return ResponseEntity.ok(watchlistGroups).getBody();
    }

    @PostMapping("/createSymbol")
    public ResponseEntity<Symbol> createSymbol(@RequestBody Symbol symbol) {
        logger.info("Creating a new symbol: {}", symbol);
        Symbol createdSymbol = dashBoardService.createSymbol(symbol);
        logger.info("Created symbol: {}", createdSymbol);
        return new ResponseEntity<>(createdSymbol, HttpStatus.CREATED);
    }

    @PostMapping("/addSymbols")
    public ResponseEntity<String> addSymbol(@RequestBody TradeRequest tradeRequest) throws ArgumentConstraintViolation {
        logger.info("Adding symbol to watchlist group: {}", tradeRequest);
        UserDTO user = dashBoardService.addSymbolToWatchlistGroups(tradeRequest);
        logger.info("Symbol added to watchlist group: {}", user);
        return ResponseEntity.ok("symbolAdded" + user);
    }

    @GetMapping("/getSymbol")
    public ResponseEntity<String> getSymbols(@RequestBody TradeRequest tradeRequest) {
        logger.info("Getting symbols for trade request: {}", tradeRequest);
        List<String> symbols = dashBoardService.getSymbolsFromWatchlistGroups(tradeRequest);
        logger.info("Retrieved symbols: {}", symbols);
        return ResponseEntity.ok("symbols" + symbols);
    }

    @PostMapping("/addOrder")
    public ResponseEntity<String> addOrder(@RequestBody TradeRequest tradeRequest) {
        logger.info("Adding an order: {}", tradeRequest);
        String response = dashBoardService.addOrder(tradeRequest);
        logger.info("Order added: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancelOrder")
    public ResponseEntity<String> cancelOrder(@RequestBody TradeRequest tradeRequest) {
        logger.info("Cancelling an order: {}", tradeRequest);
        String response = dashBoardService.cancelOrder(tradeRequest);
        logger.info("Order cancelled: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/port")
    public List<PortfolioDTO> getPortfolio() {
        logger.info("Getting portfolio information");
        List<PortfolioDTO> portfolio = dashBoardService.getPortfolio();
        logger.info("Retrieved portfolio information");
        return portfolio;
    }

    @GetMapping("/tradeHistory")
    public List<TradeHistoryDTO> getTradeHistory() {
        logger.info("Getting trade history");
        List<TradeHistoryDTO> tradeHistory = dashBoardService.getTradeHistory();
        logger.info("Retrieved trade history");
        return tradeHistory;
    }
}

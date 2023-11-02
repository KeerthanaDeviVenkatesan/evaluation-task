package com.springboot.evaluationtask.DashboardModule.service.Impl;

import com.springboot.evaluationtask.DashboardModule.Dto.*;
import com.springboot.evaluationtask.DashboardModule.enity.Order;
import com.springboot.evaluationtask.DashboardModule.enity.Symbol;
import com.springboot.evaluationtask.DashboardModule.enity.Trade;
import com.springboot.evaluationtask.DashboardModule.enity.WatchListGroup;
import com.springboot.evaluationtask.DashboardModule.exception.ArgumentConstraintViolation;
import com.springboot.evaluationtask.DashboardModule.repository.OrderRepository;
import com.springboot.evaluationtask.DashboardModule.repository.SymbolRepository;
import com.springboot.evaluationtask.DashboardModule.repository.TradeRepository;
import com.springboot.evaluationtask.DashboardModule.repository.WatchListGroupRepository;
import com.springboot.evaluationtask.DashboardModule.service.DashBoardService;
import com.springboot.evaluationtask.dto.UserDTO;
import com.springboot.evaluationtask.entity.User;
import com.springboot.evaluationtask.exception.DuplicateEntryException;
import com.springboot.evaluationtask.exception.UserNotFoundException;
import com.springboot.evaluationtask.filter.JWTFilter;
import com.springboot.evaluationtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SymbolRepository symbolsRepository;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TradeRepository tradeRepository;

    //getCurrentUser from the database

    public User getCurrentUser() {
        String userName = JWTFilter.userNameMatches;
        Optional<User> existingUser = userRepository.findByUsernameOrEmail(userName,userName);
        return existingUser.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

//get watchlistGroup for respective users
    public List<WatchlistGroupDTO> getWatchlistGroups() {
        User userInfo = getCurrentUser();
        return convertWatchlistGroupDTOs(userInfo.getWatchlists());
    }


    //addWatchlistGroup to respective Users
    @Override
    public String addWatchlistGroup(TradeRequest tradeRequest) {
        String groupName = tradeRequest.getGroupName();
        User userInfo = getCurrentUser();
        WatchListGroup watchlist = new WatchListGroup();
        watchlist.setUser(userInfo);
        watchlist.setGroupName(groupName);
        List<String> symbols = new ArrayList<>();
        watchlist.setSymbols(symbols);
        userInfo.getWatchlists().add(watchlist);
        User updatedUserInfo = userRepository.save(userInfo);

        List<WatchListGroup> watchlists = updatedUserInfo.getWatchlists();
        WatchListGroup lastWatchlist = watchlists.get(watchlists.size() - 1);
        Long id = lastWatchlist.getId();
        return "watchlist group"+id;
    }

    //WatchlistGroup to WatchlistGroupDTO
    private List<WatchlistGroupDTO> convertWatchlistGroupDTOs(List<WatchListGroup> watchlists) {
        return watchlists.stream()
                .map(this::convertWatchlistGroupDTO)
                .collect(Collectors.toList());
    }
    private WatchlistGroupDTO convertWatchlistGroupDTO(WatchListGroup group) {
        WatchlistGroupDTO groupDTO = new WatchlistGroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setGroupName(group.getGroupName());
        List<String> symbols = group.getSymbols();
        groupDTO.setSymbols(symbols != null ? new ArrayList<>(symbols) : Collections.emptyList());

        return groupDTO;
    }





    //add symbols to WatchListGroup
    @Override
public UserDTO addSymbolToWatchlistGroups(TradeRequest tradeRequest) throws ArgumentConstraintViolation {
    AddSymbol addSymbol = tradeRequest.getSymbol();
    String symbol = addSymbol.getSymbol();
    Long groupId = addSymbol.getGroupId();

    if (groupId != null) {
        User userInfo = getCurrentUser();
        WatchListGroup watchlist = checkWatchlistGroup(userInfo, groupId);
        List<String> symbols = watchlist.getSymbols();

        if (symbolsRepository.existsBySymbol(symbol)) {

            symbols.add(symbol);
            User updatedUserInfo = userRepository.save(userInfo);

            return convertToUserDTO(updatedUserInfo);
        } else {
            throw new DuplicateEntryException("Symbol not found");
        }
    } else {
        throw new ArgumentConstraintViolation("groupId cannot be null");
    }
}
//get symbols from the watchlistGroup
    @Override
public List<String> getSymbolsFromWatchlistGroups(TradeRequest tradeRequest) {
    User userInfo = getCurrentUser();
    Long groupId = tradeRequest.getGroupId();
    WatchListGroup watchlist = checkWatchlistGroup(userInfo, groupId);
    List<String> symbols = watchlist.getSymbols();

    return symbols != null ? symbols : Collections.emptyList();
}
    //watchlist group is present or not and return it
    private WatchListGroup checkWatchlistGroup(User userInfo, Long groupId) {
        return userInfo.getWatchlists().stream()
                .filter(group -> Objects.equals(group.getId(), groupId))
                .findFirst()
                .orElseThrow(() -> new DuplicateEntryException("Group id not found"));

    }
    //add order respective symbol
    @Override
    public String addOrder(TradeRequest tradeRequest) {
        Order order = tradeRequest.getOrder();
        String symbol = order.getStockSymbol();
        User currentUser = getCurrentUser();
        if (symbolsRepository.existsBySymbol(symbol)) {
            order.setUser(currentUser);
            order.setStatus("PENDING");
            order.setTimestamp(String.valueOf(LocalDateTime.now()));
            currentUser.getOrders().add(order);
            userRepository.save(currentUser);
            return "order added successfully";
        } else {
            throw new DuplicateEntryException("symbol not found");
        }
    }
    //cancel order respective symbol
    @Override
    public String cancelOrder(TradeRequest tradeRequest) {
        Order order = tradeRequest.getOrder();
        String symbol = order.getStockSymbol();
        User currentUser = getCurrentUser();
        if (symbolsRepository.existsBySymbol(symbol)) {
            order.setUser(currentUser);
            order.setStatus("CANCELED");
            order.setTimestamp(String.valueOf(LocalDateTime.now()));
            currentUser.getOrders().add(order);
            userRepository.save(currentUser);
            return "order canceled successfully";
        } else {
            throw new DuplicateEntryException("symbol not found");
        }
    }
    //execute order
    @Scheduled(fixedRate = 30000) // Run every 30 seconds
    public void executeOrder() {
        // Retrieve buy and sell orders in "PENDING" status using ORM queries
        List<Order> buyOrders = orderRepository.findByStatus("PENDING");
        List<Order> sellOrders = orderRepository.findByStatus("PENDING");

        List<Trade> executedTrades = matchOrders(buyOrders, sellOrders);

        // save trade history
        tradeRepository.saveAll(executedTrades);

        // update the status
        updateOrderStatus(buyOrders);
        updateOrderStatus(sellOrders);
    }

    private List<Trade> matchOrders(List<Order> buyOrders, List<Order> sellOrders) {
        List<Trade> executedTrades = new ArrayList<>();

        for (Order buyOrder : buyOrders) {
            for (Order sellOrder : sellOrders) {
                if (buyOrder.getPrice() >= sellOrder.getPrice()) {

                    int quantityToExchange = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());

                    // Update buy and sell orders
                    buyOrder.setQuantity(buyOrder.getQuantity() - quantityToExchange);
                    sellOrder.setQuantity(sellOrder.getQuantity() - quantityToExchange);

                    // Create Trade objects for executed trades
                    Trade buyTrade = createTrade(buyOrder, "BUY", quantityToExchange);
                    Trade sellTrade = createTrade(sellOrder, "SELL", quantityToExchange);

                    executedTrades.add(buyTrade);
                    executedTrades.add(sellTrade);

                    // Check if buy or sell order is fully executed
                    if (buyOrder.getQuantity() == 0) {
                        buyOrder.setStatus("EXECUTED");
                    }
                    if (sellOrder.getQuantity() == 0) {
                        sellOrder.setStatus("EXECUTED");
                    }
                }
            }
        }

        return executedTrades;
    }

    private Trade createTrade(Order order, String orderType, int quantity) {
        Trade trade = new Trade();
        trade.setUserTradeId(order.getUser());
        trade.setStockSymbol(order.getStockSymbol());
        trade.setOrderType(orderType);
        trade.setQuantity(quantity);
        trade.setPrice(order.getPrice());
        trade.setTimestamp(order.getTimestamp());
        return trade;
    }

    private void updateOrderStatus(List<Order> orders) {
        for (Order order : orders) {
            if (order.getQuantity() == 0) {
                order.setStatus("EXECUTED");
            }
        }
    }



//get portfolio

    public List<PortfolioDTO> getPortfolio() {
        User currentUser = getCurrentUser();
        List<Order> orders = orderRepository.findByUser(currentUser); // Fetch orders for the current user

        // Map Order entities to PortfolioDTO
        List<PortfolioDTO> portfolio = orders.stream()
                .map(this::mapToPortfolioDTO)
                .collect(Collectors.toList());

        return portfolio;
    }

    private PortfolioDTO mapToPortfolioDTO(Order order) {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setStockSymbol(order.getStockSymbol());
        portfolioDTO.setQuantity(order.getQuantity());
        portfolioDTO.setStatus(order.getStatus());
        portfolioDTO.setTimestamp(LocalDateTime.parse(order.getTimestamp()));
        return portfolioDTO;
    }
//trade history
    public List<TradeHistoryDTO> getTradeHistory() {
        List<TradeHistoryDTO> tradeHistory = new ArrayList<>();

        // Retrieve orders and map to TradeHistoryDTO
        tradeHistory.addAll(mapOrdersToTradeHistory(orderRepository.findByUserOrdersId(getCurrentUser())));

        // Retrieve trades and map to TradeHistoryDTO
        tradeHistory.addAll(mapTradesToTradeHistory(tradeRepository.findByUserTradeId(getCurrentUser())));

        return tradeHistory;
    }

    //Return all the User's Orders
    private List<TradeHistoryDTO> mapOrdersToTradeHistory(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getQuantity() > 0 && !order.getStatus().equals("EXECUTED"))
                .map(order -> createTradeHistoryItem(order.getStockSymbol(), order.getQuantity(), order.getStatus(), order.getPrice(), order.getOrderType(), order.getTimestamp()))
                .collect(Collectors.toList());
    }

    //Return all the User's Trade
    private List<TradeHistoryDTO> mapTradesToTradeHistory(List<Trade> trades) {
        return trades.stream()
                .map(trade -> createTradeHistoryItem(trade.getStockSymbol(), trade.getQuantity(), "EXECUTED", trade.getPrice(), trade.getOrderType(), trade.getTimestamp()))
                .collect(Collectors.toList());
    }

    //Create a TradeHistoryItem using TradeHistoryDTO
    private TradeHistoryDTO createTradeHistoryItem(String stockSymbol, int quantity, String orderStatus, double price, String orderType, String timestamp) {
        TradeHistoryDTO tradeHistoryItem = new TradeHistoryDTO();
        tradeHistoryItem.setStockSymbol(stockSymbol);
        tradeHistoryItem.setQuantity(quantity);
        tradeHistoryItem.setStatus(orderStatus);
        tradeHistoryItem.setPrice(price);
        tradeHistoryItem.setOrderType(orderType);
        tradeHistoryItem.setTimestamp(LocalDateTime.now());
        return tradeHistoryItem;
    }
    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setWatchListGroupDto(convertWatchlistGroupDTOs(user.getWatchlists()));
        return userDTO;
    }


}

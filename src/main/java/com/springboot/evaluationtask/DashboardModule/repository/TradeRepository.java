package com.springboot.evaluationtask.DashboardModule.repository;

import com.springboot.evaluationtask.DashboardModule.enity.Trade;
import com.springboot.evaluationtask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade,Long> {



    List<Trade> findByUserTradeId(User currentUserInfo);
}

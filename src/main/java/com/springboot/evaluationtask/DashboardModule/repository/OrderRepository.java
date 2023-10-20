package com.springboot.evaluationtask.DashboardModule.repository;

import com.springboot.evaluationtask.DashboardModule.enity.Order;
import com.springboot.evaluationtask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);


    public List<Order> findByStatus(String status);

    List<Order> findByUserOrdersId(User currentUser);
}
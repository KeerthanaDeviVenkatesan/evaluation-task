package com.springboot.evaluationtask.DashboardModule.enity;

import com.springboot.evaluationtask.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long accId;
    private String stockSymbol;
    private String orderType;
    private int quantity;
    private double price;
    private String status;
    private String timestamp;


    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}

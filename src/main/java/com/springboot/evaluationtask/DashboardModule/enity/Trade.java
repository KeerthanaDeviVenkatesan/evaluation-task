package com.springboot.evaluationtask.DashboardModule.enity;

import com.springboot.evaluationtask.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long accId;
    private String stockSymbol;
    private String orderType;
    private String timestamp;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userTradeId;

}

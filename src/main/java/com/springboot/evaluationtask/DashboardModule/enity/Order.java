package com.springboot.evaluationtask.DashboardModule.enity;

import com.springboot.evaluationtask.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @NotNull(message = "Account ID cannot be null")
    private Long accId;

    @NotBlank(message = "Stock symbol cannot be blank")
    private String stockSymbol;

    @NotBlank(message = "Order type cannot be blank")
    private String orderType;

    @Positive(message = "Quantity must be a positive number")
    private int quantity;

    @Positive(message = "Price must be a positive number")
    private double price;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotBlank(message = "Timestamp cannot be blank")
    private String timestamp;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}

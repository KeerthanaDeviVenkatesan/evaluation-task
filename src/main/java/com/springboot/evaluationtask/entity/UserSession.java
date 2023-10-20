package com.springboot.evaluationtask.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "UserSession")
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    Long sessionId;

    String username;
    String token;

    @Column(unique = true)
    Long userId;

}

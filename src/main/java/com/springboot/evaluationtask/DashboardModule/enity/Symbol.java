package com.springboot.evaluationtask.DashboardModule.enity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Symbol {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long symbolId;
    private String symbol;
    private String symbolName;


    private String indexName;
    private String companyName;
    private String industry;
    private String series;
    private String isInCode;
    private String exchange;
    private String createdAt;
    private String updatedAt;
    private String scripCode;


}

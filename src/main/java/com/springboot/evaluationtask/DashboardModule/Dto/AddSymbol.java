package com.springboot.evaluationtask.DashboardModule.Dto;


import com.springboot.evaluationtask.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AddSymbol {
    String symbol;
    Long groupId;
}

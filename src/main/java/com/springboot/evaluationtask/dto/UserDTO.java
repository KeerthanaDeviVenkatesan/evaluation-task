package com.springboot.evaluationtask.dto;

import com.springboot.evaluationtask.DashboardModule.Dto.WatchlistGroupDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long userId;
    private String username;
    private List<WatchlistGroupDTO> watchListGroupDto;


}

package com.springboot.evaluationtask.dto;

import com.springboot.evaluationtask.entity.UserSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionDTO {
    String username;
    Long id;
    UserSession existingUserSession;

}

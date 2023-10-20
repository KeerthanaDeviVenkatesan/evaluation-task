package com.springboot.evaluationtask.controller;



import com.springboot.evaluationtask.entity.User;
import com.springboot.evaluationtask.service.Impl.UserRegisterServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/register1")
public class UserRegisterController {
    @Autowired
    UserRegisterServiceImpl registerService;

    @PostMapping("/addNewUser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        registerService.registerUser(user);
        return ResponseEntity.ok("User Registered");
    }
}





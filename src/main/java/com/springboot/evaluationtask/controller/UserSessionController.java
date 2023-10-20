package com.springboot.evaluationtask.controller;


import com.springboot.evaluationtask.dto.AuthRequest;
import com.springboot.evaluationtask.service.UserSessionService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/logSession")
public class UserSessionController {
    @Autowired
    UserSessionService userSessionService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws JSONException {
        String token = userSessionService.loginUser(authRequest);
        return ResponseEntity.ok(token);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> userLogout(@RequestBody AuthRequest authRequest)  {
        userSessionService.logoutUser(authRequest);
        return ResponseEntity.ok("User logout");
    }
}

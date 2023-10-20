package com.springboot.evaluationtask.config;


import com.springboot.evaluationtask.entity.User;
import com.springboot.evaluationtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfoByUsername = repository.findByUsername(username);
        if (userInfoByUsername.isPresent()) {
            return new UserInfoUserDetails(userInfoByUsername.get());
        }
        Optional<User> userInfoByEmail = repository.findByEmail(username);
        return userInfoByEmail.map(userInfo -> new UserInfoUserDetails(userInfo, true))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"+ username));
    }
}

package com.springboot.evaluationtask.service;

import com.springboot.evaluationtask.dto.AuthRequest;
import com.springboot.evaluationtask.dto.UserSessionDTO;
import com.springboot.evaluationtask.entity.User;
import com.springboot.evaluationtask.entity.UserSession;
import com.springboot.evaluationtask.exception.DuplicateEntryException;
import com.springboot.evaluationtask.exception.UserNotFoundException;
import com.springboot.evaluationtask.repository.UserRepository;
import com.springboot.evaluationtask.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.AuthenticationException;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserSessionService {
    @Autowired
    public JWTService jwtService;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public AuthenticationManager authenticationManager;
    @Autowired
    public UserSessionRepository userSessionRepository;

    public UserSessionDTO authenticateUser(AuthRequest authRequest) {
        UserSessionDTO userSessionDto = new UserSessionDTO();
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                String userName = authRequest.getUsername();
                userSessionDto.setUsername(userName);
                Long id = userRepository.findByUsernameOrEmail(userName, userName).orElse(new User()).getUserId();
                userSessionDto.setId(id);
                userSessionDto.setExistingUserSession(userSessionRepository.findByUserId(id));
                return userSessionDto;
            }

        } catch (AuthenticationException e) {
            throw new UserNotFoundException("User not found");
        }
        return null;
    }

    public String loginUser(AuthRequest request) {
        UserSessionDTO exisitingUserSessionDto = authenticateUser(request);
        String username = exisitingUserSessionDto.getUsername();
        Long id = exisitingUserSessionDto.getId();
        if (id != null) {
            if (exisitingUserSessionDto.getExistingUserSession() == null) {
                String token = jwtService.generateToken(username);
                UserSession userSession = new UserSession();
                userSession.setUserId(id);
                userSession.setUsername(username);
                userSession.setToken(token);
                userSessionRepository.save(userSession);
                return token;
            }  else {
                throw new DuplicateEntryException("User not logged in");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public void logoutUser(AuthRequest request) {
        UserSessionDTO existingUserSessionDto = authenticateUser(request);
        Long id = existingUserSessionDto.getId();
        if (id != null) {
            if (existingUserSessionDto.getExistingUserSession() != null) {
                userSessionRepository.deleteByUserId(id);
            } else {
                throw new DuplicateEntryException("user not logged in");
            }
        } else {
            throw new UserNotFoundException("user not found");
        }
    }

}
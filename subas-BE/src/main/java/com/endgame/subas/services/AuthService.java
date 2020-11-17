package com.endgame.subas.services;


import com.endgame.subas.dto.LoginRequest;
import com.endgame.subas.dto.RegisterRequest;
import com.endgame.subas.models.Employee;
import com.endgame.subas.repository.EmployeeRepository;
import com.endgame.subas.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    public void signup(RegisterRequest registerRequest) {
        Employee employee = new Employee();
        employee.setUsername(registerRequest.getUsername());
        employee.setPassword(encodePassword(registerRequest.getPassword()));
        employeeRepository.save(employee);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        System.out.println("Line 1 : "+ loginRequest.getUsername() +" " + loginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        System.out.println("Line 2 : "+ loginRequest.getUsername() +" " + loginRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        System.out.println("Line 3 : "+ loginRequest.getUsername() +" " + loginRequest.getPassword());
        String authenticationToken = jwtProvider.generateToken(authenticate);


        System.out.println("Line 4 : "+ loginRequest.getUsername() +" " + loginRequest.getPassword());
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    public Optional<User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
}

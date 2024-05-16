package com.vansheepkohli.collegeApp.controllers;

import com.vansheepkohli.collegeApp.model.RefreshToken;
import com.vansheepkohli.collegeApp.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentController {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/user")
    public List<RefreshToken> getAllUsers() {
        return refreshTokenRepository.findAll();
    }
}

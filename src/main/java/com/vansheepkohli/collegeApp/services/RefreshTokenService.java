package com.vansheepkohli.collegeApp.services;

import com.vansheepkohli.collegeApp.model.RefreshToken;
import com.vansheepkohli.collegeApp.repositories.RefreshTokenRepository;
import com.vansheepkohli.collegeApp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    CustomUserService customUserService;

    // 600000
    public RefreshToken createRefreshToken(UserDetails userDetails) {
        UserDetails user = customUserService.loadUserByUsername(userDetails.getUsername());
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1000 * 2 * 60))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public boolean verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return true;
    }

    public List<String> generateJwtTokenFromRefreshToken(String refreshToken) {
        Optional<RefreshToken> refreshTokenFromDatabase = refreshTokenRepository.findByToken(refreshToken);
        try {
            if (refreshTokenFromDatabase.isPresent() && verifyExpiration(refreshTokenFromDatabase.get())) {
                UserDetails user = refreshTokenFromDatabase.get().getUser();
                if (user != null) {
                    List<String> newList = new ArrayList<>();
                    newList.add(jwtService.generateToken(user.getUsername()));
                    return newList;
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            RefreshToken newRefreshToken = createRefreshToken(refreshTokenFromDatabase.get().getUser());
            String newJwtToken = jwtService.generateToken(refreshTokenFromDatabase.get().getUser().getUsername());

            List<String> newList = new ArrayList<>();
            newList.add(newJwtToken);
            newList.add(newRefreshToken.getToken());
            System.out.println("new refresh token generated");
            System.out.println(newRefreshToken.getToken());
            return newList;
        }

        return null;
    }

    public Boolean logOutUser(String refreshToken) {
        Optional<RefreshToken> refreshTokenFromDatabase = refreshTokenRepository.findByToken(refreshToken);

        if(refreshTokenFromDatabase.isPresent()) {
            refreshTokenRepository.delete(refreshTokenFromDatabase.get());
            return true;
        }
        return false;
    }
}


/*
  public RefreshToken createRefreshToken(String collegeId) {
        Optional<Student> studentInfoExtracted = studentRepository.findByCollegeId(collegeId);
        RefreshToken refreshToken = RefreshToken.builder()
                .student(studentInfoExtracted.get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1000*2*60))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public boolean verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return true;
    }

    public List<String> generateJwtTokenFromRefreshToken(String refreshToken) {
        Optional<RefreshToken> refreshTokenFromDatabase = refreshTokenRepository.findByToken(refreshToken);
        try{
            if (refreshTokenFromDatabase.isPresent() && verifyExpiration(refreshTokenFromDatabase.get())) {
                Student student = refreshTokenFromDatabase.get().getStudent();
                if (student != null) {
                    List<String> newList = new ArrayList<>();
                    newList.add(jwtService.generateToken(student.getUsername()));
                    return newList;
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            RefreshToken newRefreshToken = createRefreshToken(refreshTokenFromDatabase.get().getStudent().getCollegeId());
            String newJwtToken = jwtService.generateToken(refreshTokenFromDatabase.get().getStudent().getCollegeId());

            List<String> newList = new ArrayList<>();
            newList.add(newJwtToken);
            newList.add(newRefreshToken.getToken());
            System.out.println("new refresh token generated");
            System.out.println(newRefreshToken.getToken());
            return newList;
        }

        return null;
    }
*/
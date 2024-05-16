package com.vansheepkohli.collegeApp.controllers;

import com.vansheepkohli.collegeApp.model.AuthenticationResponse;
import com.vansheepkohli.collegeApp.model.Student;
import com.vansheepkohli.collegeApp.model.Teacher;
import com.vansheepkohli.collegeApp.model.dto.StudentLoginDto;
import com.vansheepkohli.collegeApp.model.dto.TeacherLoginDto;
import com.vansheepkohli.collegeApp.services.AuthenticationService;
import com.vansheepkohli.collegeApp.services.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

private final AuthenticationService authService;
private  final RefreshTokenService refreshTokenService;

    public AuthenticationController(AuthenticationService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/student/register")
    public ResponseEntity<AuthenticationResponse> registerStudent(@RequestBody Student request) {
        System.out.println(request + "  from controller");
        return ResponseEntity.ok(authService.registerStudent(request));
    }

    @PostMapping("/student/login")
    public ResponseEntity<AuthenticationResponse> loginStudent(@RequestBody StudentLoginDto request) {
        AuthenticationResponse authenticationResponse = authService.authenticateStudent(request);
        if(authenticationResponse.getJwtToken() == null) {
            if(authenticationResponse.getErrorMessage().equals("Student not found with this College Id")) {
                return new ResponseEntity<>(authenticationResponse, HttpStatus.UNAUTHORIZED);
            }else {
                return new ResponseEntity<>(authenticationResponse, HttpStatus.NOT_FOUND);
            }
        }else {
            return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/teacher/register")
    public ResponseEntity<AuthenticationResponse> registerTeacher(@RequestBody Teacher request) {
        System.out.println(request + "  from controller");
        return ResponseEntity.ok(authService.registerTeacher(request));
    }

    @PostMapping("/teacher/login")
    public ResponseEntity<AuthenticationResponse> loginTeacher(@RequestBody TeacherLoginDto request) {
        AuthenticationResponse authenticationResponse = authService.authenticateTeacher(request);
        System.out.println(authenticationResponse + " from controller");
        if(authenticationResponse.getJwtToken() == null){
            if(authenticationResponse.getErrorMessage().equals("Username password mismatched")) {
                return new ResponseEntity<>(authenticationResponse, HttpStatus.UNAUTHORIZED);
            } else{
                return new ResponseEntity<>(authenticationResponse, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/logout/{refreshToken}")
    public ResponseEntity logOutUser(@PathVariable(name = "refreshToken") String refreshToken){
       Boolean response =  refreshTokenService.logOutUser(refreshToken);
       if(response) {
           return new ResponseEntity( HttpStatus.OK);
       }
       return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

}

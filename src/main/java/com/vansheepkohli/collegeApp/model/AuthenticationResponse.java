package com.vansheepkohli.collegeApp.model;

import com.vansheepkohli.collegeApp.model.dto.StudentDto;
import com.vansheepkohli.collegeApp.model.dto.TeacherDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthenticationResponse {
    private String jwtToken;

    private String  refreshToken;

    private StudentDto studentDto;

    private TeacherDto teacherDto;

    private String errorMessage;

    public AuthenticationResponse(String jwtToken, String errorMessage) {
        this.jwtToken = jwtToken;
        this.errorMessage = errorMessage;
    }

    public AuthenticationResponse(String jwtToken, String refreshToken, TeacherDto teacherDto) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
        this.teacherDto = teacherDto;
    }

    public AuthenticationResponse(String jwtToken, String refreshToken, StudentDto studentDto) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
        this.studentDto = studentDto;
    }


}

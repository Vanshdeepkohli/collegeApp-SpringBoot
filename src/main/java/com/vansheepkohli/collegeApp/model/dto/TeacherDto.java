package com.vansheepkohli.collegeApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDto {
    private Long id;

    private String phoneNumber;

    private String name;

    private String password;
}

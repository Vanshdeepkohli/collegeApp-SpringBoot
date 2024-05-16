package com.vansheepkohli.collegeApp.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    private Long id;


    private String collegeId;


    private String name;


    private String password;


    private Integer batch;


    private String branch;

}

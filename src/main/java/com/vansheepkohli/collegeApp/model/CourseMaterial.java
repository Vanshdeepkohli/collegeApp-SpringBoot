package com.vansheepkohli.collegeApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_material_id")
    private Long id;

    @Column(name = "course_material_url")
    private String url;

    @Column(name = "course_code")
    private String courseCode;

}

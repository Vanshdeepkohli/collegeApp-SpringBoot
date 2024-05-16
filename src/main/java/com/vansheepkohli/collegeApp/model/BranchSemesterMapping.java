package com.vansheepkohli.collegeApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "branch_semester")
public class BranchSemesterMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "semester_id",nullable = false)
    private Semester semester;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "semester_number")
    private int semesterNumber;

//    @OneToMany(mappedBy = "branchSemesterMapping", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToMany
//    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "branchSemesterMapping", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();
}

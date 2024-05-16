package com.vansheepkohli.collegeApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_course_master")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_code", unique = true)
    private String courseCode;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumns({
//            @JoinColumn(name = "branch_semester_id", referencedColumnName = "id"),
//            @JoinColumn(name = "branch_id", referencedColumnName = "branch_id"),
//            @JoinColumn(name = "semester_id", referencedColumnName = "semester_id")
//    })
//    private BranchSemesterMapping branchSemesterMapping;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumns({
//            @JoinColumn(name = "branch_semester_id", referencedColumnName = "id", nullable = false)
//    })
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private BranchSemesterMapping branchSemesterMapping;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "branch_id", referencedColumnName = "branch_id", nullable = false),
            @JoinColumn(name = "semester_id", referencedColumnName = "semester_id", nullable = false)
    })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BranchSemesterMapping branchSemesterMapping;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;



//    @Override
//    public String toString() {
//        return "[Course: { " + getId() + ", " + getCourseName() + ", " + getCourseCode() + ", " + getBranchSemesterMapping().getBranch().getName() + ", " + getTeacher() + " }]";
//    }
}

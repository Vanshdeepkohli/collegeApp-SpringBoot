package com.vansheepkohli.collegeApp.services;

import com.vansheepkohli.collegeApp.model.BranchSemesterMapping;
import com.vansheepkohli.collegeApp.model.Course;
import com.vansheepkohli.collegeApp.repositories.BranchSemesterMappingRepository;
import com.vansheepkohli.collegeApp.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private BranchSemesterMappingRepository branchSemesterMappingRepository;

    @Transactional
    public void createCourse(String courseName, String courseCode, String branchName, int semesterNumber) {
        BranchSemesterMapping branchSemesterMapping = branchSemesterMappingRepository.findBranchSemesterByBranchNameAndSemesterNumber(branchName,semesterNumber)
                .orElseThrow(() -> new IllegalArgumentException("Invalid branch semester ID"));
        Course course = Course.builder()
                .courseName(courseName)
                .courseCode(courseCode)
                .branchSemesterMapping(branchSemesterMapping)
                .build();
        courseRepository.save(course);
    }
    public Optional<Course> findCourseByCourseCode(String courseCode) {
        return courseRepository.findCourseByCourseCode(courseCode);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }
}

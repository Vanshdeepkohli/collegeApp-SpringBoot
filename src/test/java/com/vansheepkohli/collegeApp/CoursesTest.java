package com.vansheepkohli.collegeApp;

import com.vansheepkohli.collegeApp.model.Course;
import com.vansheepkohli.collegeApp.model.Teacher;
import com.vansheepkohli.collegeApp.model.dto.CourseMaterialDto;
import com.vansheepkohli.collegeApp.repositories.CourseRepository;
import com.vansheepkohli.collegeApp.repositories.TeacherRepository;
import com.vansheepkohli.collegeApp.services.CourseMaterialService;
import com.vansheepkohli.collegeApp.services.CourseService;
import com.vansheepkohli.collegeApp.services.SemesterBranchService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Slf4j
@SpringBootTest
public class CoursesTest {
    @Autowired
    private CourseMaterialService courseMaterialService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SemesterBranchService semesterBranchService;

    @Autowired
    private CourseRepository courseRepository;


    @Test
    void saveCourse(){
        courseService.createCourse("machine", "11", "IT", 7);
    }

    @Test
    void saveCourseMaterial(){

        CourseMaterialDto courseMaterialDto = CourseMaterialDto.builder()
                .url("www.google.com")
                .courseCode("1234")
                .build();
        courseMaterialService.addCourseMaterial(courseMaterialDto);
    }


    @Test
    void saveTeacher() {
        Teacher teacher = new Teacher();

        teacher.setName("vansh");
        teacher.setPhoneNumber("113343");
        teacher.setPassword("vansh");


        Teacher t = teacherRepository.save(teacher);

//        List<String> coursesCodes = new ArrayList<>();
//        coursesCodes.add("11");
        Course course = courseService.findCourseByCourseCode("11").get();
        t.getCourses().add(course);
        course.setTeacher(t);
        courseService.save(course);
        teacherRepository.save(t);

    }

    public void assignCoursesToTeacher(String phoneNumber, List<String> courseCodes) {
        Teacher teacher = teacherRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Invalid teacher ID"));

        List<Course> courses = courseRepository.findByCourseCodeIn(courseCodes);

        teacher.getCourses().addAll(courses);
        teacherRepository.save(teacher);
    }

//    public void addTeacherToCourse(String teacherPhoneNumber, String courseId) {
//        // Retrieve teacher and course from repositories
//        Teacher teacher = teacherRepository.findByPhoneNumber(teacherPhoneNumber).orElseThrow(() -> new RuntimeException("Teacher not found"));
//        Course course = courseRepository.findCourseByCourseCode(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
//
//        // Set teacher for the course
//        course.setTeacher(teacher);
//
//        // Add course to teacher's list of courses
//        teacher.getCourses().add(course);
//
//        // Save both entities to update the relationship
//        teacherRepository.save(teacher);
//        courseRepository.save(course);
//    }

    @Test
    void createSemesterBranchAssociations() {
        semesterBranchService.createSemesterBranchAssociations();
    }

}

package com.vansheepkohli.collegeApp.services;

import com.vansheepkohli.collegeApp.mappers.Mapper;
import com.vansheepkohli.collegeApp.model.*;
import com.vansheepkohli.collegeApp.model.dto.StudentDto;
import com.vansheepkohli.collegeApp.model.dto.StudentLoginDto;
import com.vansheepkohli.collegeApp.model.dto.TeacherDto;
import com.vansheepkohli.collegeApp.model.dto.TeacherLoginDto;
import com.vansheepkohli.collegeApp.repositories.CourseRepository;
import com.vansheepkohli.collegeApp.repositories.MyUserRepository;
import com.vansheepkohli.collegeApp.repositories.StudentRepository;
import com.vansheepkohli.collegeApp.repositories.TeacherRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AuthenticationService {
    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    private final Mapper<Student, StudentDto> studentDtoMapper;
    private final Mapper<Teacher, TeacherDto> teacherDtoMapper;

    private final RefreshTokenService refreshTokenService;

    private final MyUserRepository myUserRepository;

    private final CourseRepository courseRepository;


    public AuthenticationService(StudentRepository studentRepository, TeacherRepository teacherRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager, Mapper<Student, StudentDto> studentDtoMapper, Mapper<Teacher, TeacherDto> teacherDtoMapper, RefreshTokenService refreshTokenService, MyUserRepository myUserRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.studentDtoMapper = studentDtoMapper;
        this.teacherDtoMapper = teacherDtoMapper;
        this.refreshTokenService = refreshTokenService;
        this.myUserRepository = myUserRepository;
        this.courseRepository = courseRepository;
    }

    public AuthenticationResponse registerStudent(Student request) {
        if (studentRepository.findByCollegeId(request.getCollegeId()).isPresent()) {
            return new AuthenticationResponse(null, "user already exist");
        }
        Student student = new Student();
        student.setBatch(request.getBatch());
        student.setName(request.getName());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setBranch(request.getBranch());
        student.setCollegeId(request.getCollegeId());

        student = studentRepository.save(student);

        MyUser user = new MyUser();
        user.setUser(student);
        user.setName(student.getName());
        user.setRole(student.getAuthorities().toString());
        myUserRepository.save(user);


        String jwtToken = jwtService.generateToken(student.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(student);
        String refreshTokenString = refreshToken.getToken();

        StudentDto studentDto = studentDtoMapper.mapTo(student);

        return new AuthenticationResponse(jwtToken, refreshTokenString, studentDto);
    }

    public AuthenticationResponse registerTeacher(Teacher request) {
        if (teacherRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            return new AuthenticationResponse(null, "user already exist");
        }
        Teacher teacher = new Teacher();
        teacher.setName(request.getName());
        teacher.setPhoneNumber(request.getPhoneNumber());
        teacher.setCourses(request.getCourses());
//        teacher.setCourses(new ArrayList<>());
        teacher.setPassword(passwordEncoder.encode(request.getPassword()));

        teacher = teacherRepository.save(teacher);
        System.out.println(teacher);

        MyUser user = new MyUser();
        user.setUser(teacher);
        user.setName(teacher.getName());
        user.setRole(teacher.getAuthorities().toString());
        myUserRepository.save(user);

        String token = jwtService.generateToken(teacher.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(teacher);
        String refreshTokenString = refreshToken.getToken();

        TeacherDto teacherDto = teacherDtoMapper.mapTo(teacher);

        return new AuthenticationResponse(token, refreshTokenString, teacherDto);

    }


    public void assignCoursesToTeacher(String phoneNumber, List<String> courseCodes) {
        Teacher teacher = teacherRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Invalid teacher ID"));
        List<Course> courses = courseRepository.findByCourseCodeIn(courseCodes);
        teacher.setCourses(courses);
        teacherRepository.save(teacher);
    }

    public AuthenticationResponse authenticateStudent(StudentLoginDto request) {
        System.out.println(request);
        if (studentRepository.findByCollegeId(request.getCollegeId()).isEmpty()) {
            return new AuthenticationResponse(null, "Student not found with this College Id");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getCollegeId(),
                            request.getPassword()
                    )
            );
            System.out.println("inside authenticate student");
            System.out.println(authentication);

            Optional<Student> student = studentRepository.findByCollegeId(request.getCollegeId());

            String token = jwtService.generateToken(student.get().getUsername());
            System.out.println("jwt token generated");
            System.out.println(token);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(student.get());
            String refreshTokenString = refreshToken.getToken();
            System.out.println("refresh token generated");
            System.out.println(refreshTokenString);

            StudentDto studentDto = studentDtoMapper.mapTo(student.get());
            System.out.println(studentDto);
            return new AuthenticationResponse(token, refreshTokenString, studentDto);

        } catch (Exception e) {
            System.out.println(e.getMessage());
//            return new AuthenticationResponse(null, "Username password mismatched");
            return new AuthenticationResponse(null, e.getMessage());
        }

    }


    public AuthenticationResponse authenticateTeacher(TeacherLoginDto request) {
        System.out.println(request);
        if (teacherRepository.findByPhoneNumber(request.getPhoneNumber()).isEmpty()) {
            return new AuthenticationResponse(null, "Teacher not found with this phone number");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhoneNumber(),
                            request.getPassword()
                    )
            );
            System.out.println("Authentication Successful: " + authentication);

            Optional<Teacher> teacher = teacherRepository.findByPhoneNumber(request.getPhoneNumber());

            String token = jwtService.generateToken(teacher.get().getUsername());

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(teacher.get());
            String refreshTokenString = refreshToken.getToken();

            TeacherDto teacherDto = teacherDtoMapper.mapTo(teacher.get());

            return new AuthenticationResponse(token, refreshTokenString, teacherDto);

        } catch (Exception e) {
            System.out.println("Authentication Failed: " + e.getMessage());
            return new AuthenticationResponse(null, "Username password mismatched");
        }

    }


}

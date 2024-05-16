package com.vansheepkohli.collegeApp.services;

import com.vansheepkohli.collegeApp.model.Student;
import com.vansheepkohli.collegeApp.model.Teacher;
import com.vansheepkohli.collegeApp.repositories.StudentRepository;
import com.vansheepkohli.collegeApp.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = studentRepository.findByCollegeId(username)
                .map(Student::toUserDetails)
                .orElseGet(() -> teacherRepository.findByPhoneNumber(username)
                        .map(Teacher::toUserDetails)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")));
        return user;
    }


}



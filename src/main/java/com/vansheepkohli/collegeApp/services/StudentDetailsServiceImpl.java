//package com.vansheepkohli.collegeApp.services;
//
//import com.vansheepkohli.collegeApp.repositories.StudentRepository;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//@Service
//@Data
//@AllArgsConstructor
//@Component
//public class StudentDetailsServiceImpl implements UserDetailsService {
//
//
//    @Autowired
//    private final StudentRepository studentRepository;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String collegeId) throws UsernameNotFoundException {
//        return studentRepository.findByCollegeId(collegeId);
//    }
//}

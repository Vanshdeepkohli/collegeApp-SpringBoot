//package com.vansheepkohli.collegeApp.services;
//
//import com.vansheepkohli.collegeApp.repositories.TeacherRepository;
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
//public class TeacherDetailServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private TeacherRepository teacherRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
//        return teacherRepository.findByPhoneNumber(phoneNumber);
//    }
//}

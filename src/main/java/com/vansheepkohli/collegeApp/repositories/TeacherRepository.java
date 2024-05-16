package com.vansheepkohli.collegeApp.repositories;

import com.vansheepkohli.collegeApp.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByPhoneNumber(String phoneNumber);


}

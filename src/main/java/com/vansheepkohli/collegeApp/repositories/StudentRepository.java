package com.vansheepkohli.collegeApp.repositories;

import com.vansheepkohli.collegeApp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByCollegeId(String collegeId);

}

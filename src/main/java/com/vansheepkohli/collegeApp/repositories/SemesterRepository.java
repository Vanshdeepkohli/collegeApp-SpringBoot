package com.vansheepkohli.collegeApp.repositories;

import com.vansheepkohli.collegeApp.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
}

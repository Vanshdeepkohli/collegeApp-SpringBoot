package com.vansheepkohli.collegeApp.repositories;


import com.vansheepkohli.collegeApp.model.BranchSemesterMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchSemesterMappingRepository extends JpaRepository<BranchSemesterMapping, Long> {
    Optional<BranchSemesterMapping> findBranchSemesterByBranchNameAndSemesterNumber(String branchName, int semesterNumber);
}

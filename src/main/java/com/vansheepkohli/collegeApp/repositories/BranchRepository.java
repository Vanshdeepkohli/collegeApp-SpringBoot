package com.vansheepkohli.collegeApp.repositories;

import com.vansheepkohli.collegeApp.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
}

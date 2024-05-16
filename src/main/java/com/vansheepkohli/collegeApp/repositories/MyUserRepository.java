package com.vansheepkohli.collegeApp.repositories;

import com.vansheepkohli.collegeApp.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
}

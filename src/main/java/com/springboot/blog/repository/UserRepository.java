package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username ,String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    // Note all these findByUsername is case sensisite
    // findByUserName is not same as findByUsername
    // if you have your variable defined as UserName then write findByUserName
    // if you have your varible defined as username then write findByUsername
}

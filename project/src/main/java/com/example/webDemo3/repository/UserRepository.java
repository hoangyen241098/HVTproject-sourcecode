package com.example.webDemo3.repository;

import com.example.webDemo3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findUserByUsername(String username);
    void deleteByUsername(String username);

    @Query("select u.username from User u")
    List<String> findAllUsername();
}

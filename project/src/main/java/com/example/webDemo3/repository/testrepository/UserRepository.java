package com.example.webDemo3.repository.testrepository;

import com.example.webDemo3.entity.testentity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
}

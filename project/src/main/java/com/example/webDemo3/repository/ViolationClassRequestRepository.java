package com.example.webDemo3.repository;

import com.example.webDemo3.entity.ViolationClass;
import com.example.webDemo3.entity.ViolationClassRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViolationClassRequestRepository extends JpaRepository<ViolationClassRequest, Integer> {
}

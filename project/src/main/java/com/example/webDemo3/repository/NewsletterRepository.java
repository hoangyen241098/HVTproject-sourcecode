package com.example.webDemo3.repository;

import com.example.webDemo3.entity.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * lamnt98
 * 27/07
 */
@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter,Integer> {
}

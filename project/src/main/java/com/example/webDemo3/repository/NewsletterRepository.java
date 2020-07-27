package com.example.webDemo3.repository;

import com.example.webDemo3.entity.Newsletter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * lamnt98
 * 27/07
 */
@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter,Integer> {
    @Query(value = "select n from Newsletter n where n.status = 1 order by n.gim desc")
    Page<Newsletter> loadAllLetter(Pageable paging);
}

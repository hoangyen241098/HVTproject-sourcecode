package com.example.webDemo3.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * lamnt98
 * 01/07
 */
@Entity
@Table(name = "SCHOOL_YEARS")
@Data
public class SchoolYear {

    @Id
    @Column(name = "YEAR_ID")
    private Integer yearID;

    @Column(name = "FROM_DATE")
    private Date fromDate;

    @Column(name = "TO_DATE")
    private Date toDate;

    @Column(name = "YEAR")
    private String year;
}

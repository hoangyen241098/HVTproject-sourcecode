package com.example.webDemo3.entity;

import lombok.Data;

import javax.persistence.*;
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer yearID;

    @Column(name = "FROM_DATE")
    private Date fromDate;

    @Column(name = "TO_DATE")
    private Date toDate;

    @Column(name = "YEAR_FROM")
    private Integer fromYear;

    @Column(name = "YEAR_TO")
    private Integer toYear;
}

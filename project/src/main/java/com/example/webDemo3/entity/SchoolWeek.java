package com.example.webDemo3.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

/**
 * lamnt98
 * 01/07
 */
@Entity
@Table(name = "SCHOOL_WEEKS")
@Data
public class SchoolWeek {

    @Id
    @Column(name = "WEEK_ID")
    private Integer weekID;

    @Column(name = "Month_ID")
    private Integer monthID;

    @Column(name = "WEEK")
    private Integer week;
}

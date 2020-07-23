package com.example.webDemo3.entity;

import lombok.Data;
import javax.persistence.*;

/*
kimpt142 - 23/07
 */
@Entity
@Table(name = "SCHOOL_MONTHS")
@Data
public class SchoolMonth {

    @Id
    @Column(name = "MONTH_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer monthId;

    @Column(name = "SEMESTER_ID")
    private Integer semesterId;

    @Column(name = "MONTH")
    private Integer month;

    @Column(name = "YEAR_ID")
    private Integer yearId;
}

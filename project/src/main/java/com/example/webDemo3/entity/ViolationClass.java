package com.example.webDemo3.entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "VIOLATION_CLASSES")
public class ViolationClass {

    @Id
    @Column(name = "VIOLATION_CLASS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CLASS_ID")
    private Integer classId;
//    @ManyToOne
//    @JoinColumn(name = "CLASS_ID")
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private Class classSchool;

    //        @Column(name = "VIOLATION_ID")
//    private Integer violationId;
    @ManyToOne
    @JoinColumn(name = "VIOLATION_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Violation violation;


    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "WEEK_ID")
    private Integer weekId;

    //    @Column(name = "YEAR_ID")
//    private Integer yearId;
    @ManyToOne
    @JoinColumn(name = "YEAR_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SchoolYear year;

    //    @Column(name = "DAY_ID")
//    private Integer dayId;
    @ManyToOne
    @JoinColumn(name = "DAY_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Day day;

    @Column(name = "CREATE_BY")
    private String createBy;

    @Column(name = "HISTORY")
    private String history;
}

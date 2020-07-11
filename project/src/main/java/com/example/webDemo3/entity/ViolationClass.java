package com.example.webDemo3.entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

/**
 * lamnt98 - 27/06
 */
@Entity
@Data
@Table(name = "VIOLATION_CLASSES")
public class ViolationClass {

    @Id
    @Column(name = "VIOLATION_CLASS_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CLASS_ID")
    private Integer classId;

    @Column(name = "VIOLATION_ID")
    private Integer violatinId;

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

    @Column(name = "YEAR_ID")
    private Integer yearId;

    @Column(name = "DAY_ID")
    private Integer dayId;

}

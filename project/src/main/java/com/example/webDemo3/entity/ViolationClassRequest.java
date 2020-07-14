package com.example.webDemo3.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "VIOLATION CLASS_REQUETS")
public class ViolationClassRequest {
    @Id
    @Column(name = "REQUEST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;

    @Column(name = "VIOLATION_CLASS_ID")
    private Integer violationClassId;

    @Column(name = "CREAT_BY")
    private String creatBy;

    @Column(name = "QUANTITY_NEW")
    private Integer quantityNew;

    @Column(name = "DATE_CHANGE")
    private Date dateChange;

    @Column(name = "REASON")
    private String reason;

    @Column(name = "STATUS_CHANGE")
    private String statusChange;

}

package com.example.webDemo3.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "SCHOOL_RANK_WEEKS")
@Data
public class SchoolRankWeek {

    @EmbeddedId
    private SchoolRankWeekId schoolRankWeekId;

    @Column(name = "EMULATION_GRADE")
    private Float emulationGrade;

    @Column(name = "LEARNING_GRADE")
    private Float learningGrade;

    @Column(name = "MOVEMENT_GRADE")
    private Float movementGrade;

    @Column(name = "LABOR_GRADE")
    private Float laborGrade;

    @Column(name = "TOTAL_GRADE")
    private Float totalGrade;

    @Column(name = "RANK")
    private Integer rank;

    @Column(name = "HISTORY")
    private String history;
}

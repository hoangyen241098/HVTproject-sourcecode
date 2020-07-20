package com.example.webDemo3.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;

@Data
@Embeddable
public class SchoolRankWeekId implements Serializable {
    @Column
    private Integer WEEK_ID;

    @Column
    private Integer CLASS_ID;
}

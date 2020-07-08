package com.example.webDemo3.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * lamnt98 - 07/07
 */
@Entity
@Data
@Table(name = "CLASS_RED_STARS")
public class ClassRedStar {

    @Column(name = "CLASS_ID")
    private Integer classId;

    @EmbeddedId
    private ClassRedStarId classRedStarId;
}

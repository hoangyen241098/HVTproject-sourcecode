package com.example.webDemo3.entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "CLASSES")
public class Class {
    @Id
    @Column(name = "CLASS_ID")
    private Integer classId;

    @Column(name = "GRADE")
    private String grade;

    @Column(name = "GIFTED_CLASS")
    private String giftedClass;
}

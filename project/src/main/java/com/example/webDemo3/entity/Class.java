package com.example.webDemo3.entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * lamnt98 - 27/06
 */
@Entity
@Data
@Table(name = "CLASSES")
public class Class {
    @Id
    @Column(name = "CLASS_ID")
    private Integer classId;

    @Column(name = "GRADE")
    private String grade;

    @Column(name = "MAPPING_NAME")
    private String mappingName;

    @Column(name = "STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "GIFTED_CLASS_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private GiftedClass giftedClass;
}

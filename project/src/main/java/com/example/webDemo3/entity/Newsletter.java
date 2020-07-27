package com.example.webDemo3.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * lamnt98 - 27/07
 */
@Entity
@Data
@Table(name = "NEWSLETTERS")
public class Newsletter {

    @Id
    @Column(name = "NEWSLETTER_ID")
    private Integer newsletterId;

    @Column(name = "FROM_USER")
    private String userName;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "HEADER")
    private String header;

    @Column(name = "HEADER_IMAGE")
    private String headerImage;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "GIM")
    private Integer gim;

    @Column(name = "STATUS")
    private Integer status;


}

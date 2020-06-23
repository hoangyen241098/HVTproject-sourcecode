package com.example.webDemo3.entity.testentity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "USERS")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -297553281792804396L;

    @Id
    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "PASS_WORD")
    private String password;

    @Column(name = "CLASS")
    private String classUser;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;
}
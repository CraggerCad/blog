package com.treeleaf.task.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by User on 2/12/2022.
 */

@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role", nullable = false)
    private String role;



}

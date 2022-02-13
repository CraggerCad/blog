package com.treeleaf.task.model;



import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(unique = true, nullable = false, name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(nullable = false, name = "isEnabled")
    private boolean enabled;

    @Column(nullable = false, name = "isDeleted")
    private boolean deleted;

    @ManyToMany(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles")
    @Cascade(CascadeType.MERGE)
    private List<Role> roles;


}

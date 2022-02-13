package com.treeleaf.task.model;

import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by User on 2/12/2022.
 */
@Entity
@Table(name = "blog")
@Data
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String blog;

    @OneToOne
    private User createdBy;

    private boolean deleted;

    @OneToMany
    @CollectionTable(name = "blog_comments")
    @Cascade(CascadeType.ALL)
    private List<Comment> comments;


}

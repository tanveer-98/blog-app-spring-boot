package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@AllArgsConstructor : @AllArgsConstructor generates a constructor with 1 parameter for each field in your class.

// @NoArgsConstructor : when we create a JPA entity with an argument constructor then we need
// no arg constructor because Hibernate internally uses POJI  to create objects

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name="posts", uniqueConstraints= {
                @UniqueConstraint(columnNames = {"title"})
}
)
public class Post {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id ;

    @Column(name="title" , nullable = false)
    private String title ;

    @Column(name="description" , nullable = false)
    private String description;

    @Column(name="content" , nullable = false)
    private String content;

    // orphan removal means whenever parent is removed the child is also removed
    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL ,  orphanRemoval = true)
    @Column(name="comments" )
    private List<Comment> comments = new ArrayList<>();

}

package com.springboot.blog.entity;


import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name ="comments")
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String body;

    // One to Many relationship between post and comment

    // NOTE:  The FetchType.LAZY tells Hibernate to only Fetch the related entities from the
    //  database when you use the relationship


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="post_id" , nullable = false)
    private Post post;

}

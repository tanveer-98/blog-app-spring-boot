package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name ;

    @Column(nullable = false , unique = true)
    private String username;

    @Column(nullable = false , unique = true)
    private String email;


    @Column(nullable = false , unique = true)
    private String password;


    // roles of a user
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name ="user_roles",
    joinColumns = @JoinColumn(name="user_id" , referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="role_id",  referencedColumnName ="id"))
    private Set<Role> roles;

    // WHAT DOES FETCHTYPE.EAGER MEAN ?  answer : when an entity object is retrieved, any related entities marked as EAGER will also be fetched along with it, regardless of whether you explicitly access those related entities in your code.

}

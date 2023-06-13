package com.springboot.blog.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



// NOTE : we can also use @Data annotation. But that will generate hashCode and toString methods
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @Column(nullable =false)
    private String name;
}

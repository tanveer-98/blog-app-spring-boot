package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {
    private long id ;

    @NotEmpty(message="Name should not be empty")
    private String name;

    @NotEmpty(message="Email should not be empty")
    private String email;

    @NotEmpty(message="Body should not be empty")
    private String body;
}

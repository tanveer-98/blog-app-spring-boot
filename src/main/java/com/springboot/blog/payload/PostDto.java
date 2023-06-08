package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data // automatically generates all the getters and setters
public class PostDto {
    private long id ;

    // title should nto be null or empty
    // title  should have at least 2 characters

    @NotEmpty
    @Size(min = 2 , message = "Post title should have at least 2 characters")
    private String title ;

    // post content should not be null or empty

    @NotEmpty(message="content must not be empty")
    private String content;

    // post description sholuld be not null or empty
    // post description should have atleast 10 characters
    @NotEmpty
    @Size(min = 10,message = "Post description should have atleast 10 characters ")
    private String description;


    private List<CommentDto> comments;
}


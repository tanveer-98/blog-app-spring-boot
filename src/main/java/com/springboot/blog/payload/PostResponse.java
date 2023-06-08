package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comments;

import java.util.List;
import java.util.Set;


// data annotaction from lombok automatically generates the getters and setters

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto>  content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}

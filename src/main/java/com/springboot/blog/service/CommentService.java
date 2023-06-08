package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;



public interface CommentService {
    CommentDto createComment(long postId , CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(long post_id, long commentId);
    CommentDto updateCommentById(long post_id  , long commentId , CommentDto comment);
    void deleteComment(long post_id , long commentId);
//    CommentDto deleteAllComment(long post_id);
}

package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.hibernate.annotations.Comments;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springboot.blog.service.PostService;
import java.util.List;


@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }


    // add new comment for postId
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
        @PathVariable(value= "postId")   long postId,
        @Valid @RequestBody CommentDto commentDto
    ){
        return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto) , HttpStatus.CREATED);

    }

    // get all comments from given post from post id

    @GetMapping("posts/{post_id}/comments")
    public List<CommentDto> getCommentsByPostId(
            @PathVariable(name="post_id") long post_id
    ){
        return  commentService.getCommentsByPostId(post_id);

    }

    // get comment by Id

    @GetMapping("posts/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(name="post_id") long post_id ,
            @PathVariable(name="comment_id") long comment_id
    ){
//        return commentService.getCommentById(post_id , comment_id);
        // above is also correct , better approach is the below code

        CommentDto commentDto = commentService.getCommentById(post_id,comment_id);
        return new ResponseEntity<CommentDto>(commentDto, HttpStatus.FOUND);
    }


    // /// UPDATE COMMENT USING ID

    @PutMapping("posts/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDto> updateCommentById(
            @PathVariable(name="post_id") long post_id,
            @PathVariable(name="comment_id") long comment_id,
            @Valid @RequestBody CommentDto commentDto
    ){
     CommentDto updatedComment = commentService.updateCommentById(post_id,comment_id ,commentDto);

     return new ResponseEntity<CommentDto>(updatedComment,HttpStatus.OK);


    }

    // DELETE COMMENT USING ID

    @DeleteMapping("posts/{post_id}/comments/{comment_id}")
    public ResponseEntity<String> deleteComment(
        @PathVariable(name="post_id") long post_id,
        @PathVariable(name="comment_id") long comment_id
    ){
        commentService.deleteComment(post_id, comment_id);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted");
    }

}

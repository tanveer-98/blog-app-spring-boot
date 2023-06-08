package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.blog.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;


    //declare the modelMapper


    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository
        ,ModelMapper mapper
    ){
         this.commentRepository = commentRepository;
         this.postRepository = postRepository;
         this.mapper  = mapper;
    }




    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id
        // NOTE : post is

        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post","id" , postId));

        // set post to Comment Entity

        comment.setPost(post);

        // save commment entity to the database

        commentRepository.save(comment);

        return mapToDTO(comment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

         // retreive comments by postId

        List<Comment> allcomments = commentRepository.findByPostId(postId);


        // convert list of comment entries into list of comment Dto
        return allcomments.stream().map((comment)->mapToDTO(comment)).collect(Collectors.toList());
//        return null;
    }

    @Override
    public CommentDto getCommentById(long post_id, long commentId) {
    // retrieve post entity by id
        Post post  = postRepository.findById(post_id).orElseThrow(()->new
                ResourceNotFoundException("post","id", post_id));
        // post was found

        // retreive comment by id

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
             new ResourceNotFoundException("comment","commentId", commentId));

        // bad request case :

        if(!comment.getPost().getId().equals(post.getId())){
            // checked exception
            throw new BlogAPIException(HttpStatus.BAD_REQUEST , "Comment does not belong to the post" );
        }

         return mapToDTO(comment);
    }

    @Override
    public CommentDto updateCommentById(long post_id, long commentId, CommentDto commentUpdate) {
        // retrieve post entity by id
        Post post  = postRepository.findById(post_id).orElseThrow(()->new
                ResourceNotFoundException("post","id", post_id));
        // post was found

        // retreive comment by id

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("comment","commentId", commentId));

        // bad request case :

        if(!comment.getPost().getId().equals(post.getId())){
            // checked exception
            throw new BlogAPIException(HttpStatus.BAD_REQUEST , "Comment does not belong to the post" );
        }

        comment.setName(commentUpdate.getName());
        comment.setBody(commentUpdate.getBody());
        comment.setEmail(commentUpdate.getEmail());

        // You save Entity in the database and then send back a DTO object to the view layer
        Comment  updatedComment = commentRepository.save(comment);

        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(long post_id, long commentId) {
        // retrieve post entity by id
        Post post  = postRepository.findById(post_id).orElseThrow(()->new
                ResourceNotFoundException("post","id", post_id));
        // post was found

        // retreive comment by id

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("comment","commentId", commentId));

        // bad request case :

        if(!comment.getPost().getId().equals(post.getId())){
            // checked exception
            throw new BlogAPIException(HttpStatus.BAD_REQUEST , "Comment does not belong to the post" );
        }
        commentRepository.delete(comment);
    }

//    return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted");


    private CommentDto mapToDTO(Comment comment){
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
//        commentDto.setName(comment.getName());

        CommentDto commentDto = mapper.map(comment, CommentDto.class);

        return commentDto;

    }

    private Comment mapToEntity(CommentDto commentDto){
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());

        Comment comment = mapper.map(commentDto , Comment.class);

        return comment;
    }
}

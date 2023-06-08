package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int PageNo , int PageSize , String sortBy, String sortDir);

    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto , long id);
    List<PostDto> deletePostById(long id);

}

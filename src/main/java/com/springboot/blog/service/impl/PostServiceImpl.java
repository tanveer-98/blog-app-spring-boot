package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {




    // constructor based de pendency injection
    // Note : Autowired annotation for constructor can be ommmited when there is only one constructor
    private PostRepository postRepository;
    private CommentRepository commentRepository;


    /// declare teh model mapper to convert DTO-Entity vis a vis

   private ModelMapper mapper;


    //    @Autowired // can be omitted
    public PostServiceImpl(PostRepository postRepository
     , ModelMapper mapper, CommentRepository commentRepository
    ) {
        this.postRepository = postRepository;
         this.mapper = mapper;
         this.commentRepository = commentRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert dto to entity
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);


        // convert entity to dto to return back the post

        PostDto postResponse = mapToDTO(newPost);


        return  postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize , String sortBy , String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize , Sort.by(sortBy));

        Page<Post>  posts = postRepository.findAll(pageable);

        // get content for page object

        List<Post> listofPosts = posts.getContent();

        List<PostDto> content =  listofPosts.stream().map(post->mapToDTO(post)).collect(Collectors.toList());


        // get all the comments


        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());


        System.out.println(postResponse);
        return postResponse;
    }

//    @Override
//    public List<PostDto> getAllPosts() {
//
//        List<Post> posts = postRepository.findAll();
//
//        return  posts.stream().map((post)->mapToDTO(post)).collect(Collectors.toList());
//
//    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post" , "id" , id));
        List<Comment> allcomments = commentRepository.findByPostId(id);
        post.setComments(allcomments);

        return mapToDTO(post);
    }


    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // get post by id from the database
        // if post not found then throw an exception
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post" , "id" , id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        return mapToDTO(updatedPost);
    }



    @Override
    public List<PostDto> deletePostById(long id) {
       Optional<Post> post  = postRepository.findById(id);

       if(!post.isPresent()){

           return getAllPosts(1, 1 ,  "" ,"").getContent();
       }

       // delete the post
        postRepository.deleteById(id);
        return getAllPosts(1,5 ,""  , "").getContent();

    }

    private PostDto mapToDTO(Post post){



        // helper function to convert entity to DTO
//        PostDto postDto = new PostDto();
//
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setContent(post.getContent());
//        postDto.setDescription(post.getDescription());

        // using model mapper
        PostDto postDto = mapper.map(post,PostDto.class);

        return postDto;
    }

    private Post mapToEntity(PostDto postDto){

        // helper function to convert DTO to entity
//        Post post = new Post ();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setId(postDto.getId());
//        post.setContent(postDto.getContent());

        // using model mapper

        Post post = mapper.map(postDto, Post.class);


        return post;
    }




}

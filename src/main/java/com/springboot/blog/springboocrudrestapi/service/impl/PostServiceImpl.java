package com.springboot.blog.springboocrudrestapi.service.impl;

import com.springboot.blog.springboocrudrestapi.entity.Post;
import com.springboot.blog.springboocrudrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springboocrudrestapi.payload.PostDto;
import com.springboot.blog.springboocrudrestapi.repository.PostRepository;
import com.springboot.blog.springboocrudrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post = mapToEntity(postDto);


        Post newPost =  postRepository.save(post);

        // convert entity to DTO

        PostDto postResponse = mapToDTO(newPost);


        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts(PostDto postDto) {

       List<Post> posts = postRepository.findAll();

       return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

    }

    @Override
    public PostDto getPostById(long id) {
      Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }


    // convert entity to DTO
    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(postDto.getDescription());

        return postDto;
    }

    // convert Dto to Entity
    private Post mapToEntity(PostDto postDto){

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());




        return post;
    }


}

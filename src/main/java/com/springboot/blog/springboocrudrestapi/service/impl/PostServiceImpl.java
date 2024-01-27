package com.springboot.blog.springboocrudrestapi.service.impl;

import com.springboot.blog.springboocrudrestapi.entity.Post;
import com.springboot.blog.springboocrudrestapi.payload.PostDto;
import com.springboot.blog.springboocrudrestapi.repository.PostRepository;
import com.springboot.blog.springboocrudrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost =  postRepository.save(post);

        // convert entity to DTO

        PostDto postResponse = new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent((newPost.getContent()));

        return postResponse;
    }
}

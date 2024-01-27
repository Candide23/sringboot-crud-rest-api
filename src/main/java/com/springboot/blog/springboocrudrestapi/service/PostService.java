package com.springboot.blog.springboocrudrestapi.service;

import com.springboot.blog.springboocrudrestapi.payload.PostDto;

public interface PostService {

    PostDto createPost(PostDto postDto);
}

package com.springboot.blog.springboocrudrestapi.service;

import com.springboot.blog.springboocrudrestapi.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

   List<PostDto> getAllPosts(PostDto postDto);

   PostDto getPostById(long id);

   PostDto updatePosts(PostDto postDto, long id);

   void deletePosts(long id);
}

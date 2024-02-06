package com.springboot.blog.springboocrudrestapi.service;

import com.springboot.blog.springboocrudrestapi.payload.CommentDto;

public interface CommentService {

    public CommentDto createComment(long postId, CommentDto commentDto);
}

package com.springboot.blog.springboocrudrestapi.service;

import com.springboot.blog.springboocrudrestapi.payload.CommentDto;
import com.springboot.blog.springboocrudrestapi.payload.PostDto;

import java.util.List;

public interface CommentService {

     CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentsById(Long postId, Long commentId);

    CommentDto updateComments( Long postId, Long commentId, CommentDto commentRequest);
}

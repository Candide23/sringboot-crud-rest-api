package com.springboot.blog.springboocrudrestapi.service.impl;

import com.springboot.blog.springboocrudrestapi.entity.Comment;
import com.springboot.blog.springboocrudrestapi.entity.Post;
import com.springboot.blog.springboocrudrestapi.exception.BlogAPIException;
import com.springboot.blog.springboocrudrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springboocrudrestapi.payload.CommentDto;
import com.springboot.blog.springboocrudrestapi.payload.PostDto;
import com.springboot.blog.springboocrudrestapi.repository.CommentRepository;
import com.springboot.blog.springboocrudrestapi.repository.PostRepository;
import com.springboot.blog.springboocrudrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;


    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // set post to comment entity
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);


        CommentDto commentResponse = mapToDTO(newComment);

        return commentResponse;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
      //retrieve comments by postId

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());


    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","postId",postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","Id", commentId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doest not belong to post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComments(Long postId, Long commentId, CommentDto commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","postId",postId));


        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","Id", commentId));

        if(!comment.getPost().getId().equals(post.getId())) {
            new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doest not belong to post");
        }

        comment.setBody(commentRequest.getBody());
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());

        Comment updateComment = commentRepository.save(comment);

        return mapToDTO(updateComment);
    }


    private CommentDto mapToDTO( Comment comment ){

        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());

      return commentDto;

    }

    private Comment mapToEntity( CommentDto commentDto){

        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        return comment;


    }
}

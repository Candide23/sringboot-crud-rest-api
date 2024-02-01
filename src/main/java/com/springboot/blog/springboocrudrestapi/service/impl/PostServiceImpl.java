package com.springboot.blog.springboocrudrestapi.service.impl;

import com.springboot.blog.springboocrudrestapi.entity.Post;
import com.springboot.blog.springboocrudrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springboocrudrestapi.payload.PostDto;
import com.springboot.blog.springboocrudrestapi.payload.PostResponse;
import com.springboot.blog.springboocrudrestapi.repository.PostRepository;
import com.springboot.blog.springboocrudrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy, String sortDir ) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

       Page<Post> posts = postRepository.findAll(pageable);

       List<Post> listOfPosts = posts.getContent();

       List<PostDto> content =  posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageSize(posts.getSize());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;


    }

    @Override
    public PostDto getPostById(long id) {
      Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePosts(PostDto postDto, long id) {
       // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);

        return mapToDTO(updatePost);



    }

    @Override
    public void deletePosts(long id) {

        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);


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

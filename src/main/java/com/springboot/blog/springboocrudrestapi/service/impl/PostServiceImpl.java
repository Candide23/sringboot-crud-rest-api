package com.springboot.blog.springboocrudrestapi.service.impl;

import com.springboot.blog.springboocrudrestapi.entity.Category;
import com.springboot.blog.springboocrudrestapi.entity.Post;
import com.springboot.blog.springboocrudrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springboocrudrestapi.payload.PostDto;
import com.springboot.blog.springboocrudrestapi.payload.PostResponse;
import com.springboot.blog.springboocrudrestapi.repository.CategoryRepository;
import com.springboot.blog.springboocrudrestapi.repository.PostRepository;
import com.springboot.blog.springboocrudrestapi.service.PostService;
import org.modelmapper.ModelMapper;
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
    private ModelMapper mapper;

    private CategoryRepository categoryRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }



    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        // convert DTO to entity
        Post post = mapToEntity(postDto);

        post.setCategory(category);
        Post newPost =  postRepository.save(post);

        // convert entity to DTO

        PostDto postResponse = mapToDTO(newPost);


        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy, String sortDir ) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content= listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }

    @Override
    public List<PostDto> getPostByCategories(long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> postList = postRepository.findByCategoryId(categoryId);

        return postList.stream().map(post -> mapToDTO(post) ).collect(Collectors.toList());

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

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setCategory(category);
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

        PostDto postDto = mapper.map(post,PostDto.class);

          //PostDto postDto = new PostDto();
          //postDto.setId(post.getId());
          //postDto.setTitle(post.getTitle());
          //postDto.setDescription(post.getDescription());
          //postDto.setContent(post.getContent());
        return postDto;
    }

    // convert Dto to Entity
    private Post mapToEntity(PostDto postDto){

        Post post = mapper.map(postDto, Post.class);

          /*Post post = new Post();
          post.setTitle(postDto.getTitle());
          post.setDescription(postDto.getDescription());
          post.setContent(postDto.getContent());*/
          return post;
    }


}

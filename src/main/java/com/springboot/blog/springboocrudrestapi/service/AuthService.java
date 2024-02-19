package com.springboot.blog.springboocrudrestapi.service;

import com.springboot.blog.springboocrudrestapi.payload.LoginDto;
import com.springboot.blog.springboocrudrestapi.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}

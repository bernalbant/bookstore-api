package com.bookstore.controller;

import com.bookstore.model.entity.User;
import com.bookstore.model.request.LoginRequest;
import com.bookstore.security.TokenService;
import com.bookstore.service.UserService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private TokenService tokenService;
  private UserService userService;

  public UserController(TokenService tokenService, UserService userService) {
    this.tokenService = tokenService;
    this.userService = userService;
  }

  @PostMapping("/login")
  public String login(@Valid @RequestBody LoginRequest loginRequest) {
    User user = userService.login(loginRequest);
    return tokenService.generateToken(user);
  }
}

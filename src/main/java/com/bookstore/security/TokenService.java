package com.bookstore.security;

import com.bookstore.model.entity.User;

public interface TokenService {

  String generateToken(User user);

  com.bookstore.security.UserPrincipal parseToken(String token);
}

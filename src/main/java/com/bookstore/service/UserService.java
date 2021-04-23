package com.bookstore.service;

import com.bookstore.model.entity.User;
import com.bookstore.model.request.LoginRequest;

public interface UserService {

  User login(LoginRequest loginRequest);
}

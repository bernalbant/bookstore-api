package com.bookstore.service;

import static com.bookstore.constant.ExceptionConstant.USER_NOT_FOUND_MESSAGE;
import static com.bookstore.constant.ExceptionConstant.WRONG_PASSWORD_MESSAGE;

import com.bookstore.exception.NotFoundException;
import com.bookstore.model.entity.User;
import com.bookstore.model.request.LoginRequest;
import com.bookstore.repository.UserRepository;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User login(LoginRequest loginRequest) {
    User user = userRepository.findByUsername(loginRequest.getUsername());

    if (Objects.nonNull(user)) {
      if (!user.getPassword().equalsIgnoreCase(loginRequest.getPassword())) {
        throw new NotFoundException(WRONG_PASSWORD_MESSAGE);
      }
      return user;
    } else {
      throw new NotFoundException(USER_NOT_FOUND_MESSAGE);
    }
  }
}

package com.bookstore.service.impl;

import com.bookstore.exception.NotFoundException;
import com.bookstore.model.entity.User;
import com.bookstore.model.request.LoginRequest;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.UserServiceImpl;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @ParameterizedTest
  @MethodSource("provideUsers")
  public void itShouldLogin(User user, LoginRequest loginRequest) {
    Mockito.when(userRepository.findByUsername(Mockito.anyString()))
        .thenReturn(user);

    User loginUser = userService.login(loginRequest);

    Assertions.assertThat(loginUser.getId()).isEqualTo(user.getId());
    Assertions.assertThat(loginUser.getPassword()).isEqualTo(user.getPassword());
    Assertions.assertThat(loginUser.getUsername()).isEqualTo(user.getUsername());
  }

  @ParameterizedTest
  @MethodSource("provideExceptionUsers")
  public void itShouldThrowNotFoundExceptionWhenGivenPasswordIsWrong(User user, LoginRequest loginRequest) {
    Mockito.when(userRepository.findByUsername(Mockito.anyString()))
        .thenReturn(user);

    NotFoundException notFoundException = Assertions.catchThrowableOfType(() ->
        userService.login(loginRequest), NotFoundException.class);

    Assertions.assertThat(notFoundException).isNotNull();
    Assertions.assertThat(notFoundException.getMessage()).isEqualTo("Password is wrong");
  }

  @ParameterizedTest
  @MethodSource("provideExceptionUsers")
  public void itShouldThrowNotFoundExceptionWhenGivenUsernameIsWrong(User user, LoginRequest loginRequest) {
    Mockito.when(userRepository.findByUsername(Mockito.anyString()))
        .thenReturn(null);

    NotFoundException notFoundException = Assertions.catchThrowableOfType(() ->
        userService.login(loginRequest), NotFoundException.class);

    Assertions.assertThat(notFoundException).isNotNull();
    Assertions.assertThat(notFoundException.getMessage()).isEqualTo("User not found");
  }

  private static Stream<Arguments> provideUsers() {
    User user = new User();
    user.setId(1);
    user.setPassword("123456");
    user.setUsername("username");

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("admin");
    loginRequest.setPassword("123456");

    return Stream.of(
        Arguments.of(user, loginRequest)
    );
  }

  private static Stream<Arguments> provideExceptionUsers() {
    User user = new User();
    user.setId(1);
    user.setPassword("123456");
    user.setUsername("username");

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("admin");
    loginRequest.setPassword("1236");

    return Stream.of(
        Arguments.of(user, loginRequest)
    );
  }
}

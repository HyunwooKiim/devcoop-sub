package main.application.user.internal;

import main.application.user.dto.request.*;
import main.application.user.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto signUp(UserSignUpRequestDto request);
    UserResponseDto logIn(UserLogInRequestDto request);
}

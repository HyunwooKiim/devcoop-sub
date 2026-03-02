package main.adapter.user;

import main.adapter.common.ApiResponse;
import main.application.user.dto.request.UserLogInRequestDto;
import main.application.user.dto.request.UserSignUpRequestDto;
import main.application.user.dto.response.UserResponseDto;
import main.application.user.exception.UserNotFoundException;
import main.application.user.internal.UserService;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST /api/users/signup
     * 사용자 회원가입
     */
    public ApiResponse<UserResponseDto> signUp(String name, String email, String password) {
        try {
            if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                return ApiResponse.badRequest("Name, email, and password are required");
            }

            UserSignUpRequestDto request = new UserSignUpRequestDto(name, email, password);
            UserResponseDto response = userService.signUp(request);
            return ApiResponse.created(response);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * POST /api/users/login
     * 사용자 로그인
     */
    public ApiResponse<UserResponseDto> logIn(String email, String password) {
        try {
            if (email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                return ApiResponse.badRequest("Email and password are required");
            }

            UserLogInRequestDto request = new UserLogInRequestDto(email, password);
            UserResponseDto response = userService.logIn(request);
            return ApiResponse.success("Login successful", response);
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound("User not found or invalid password");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}

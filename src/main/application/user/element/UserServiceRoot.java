package main.application.user.element;

import main.application.user.dto.request.UserLogInRequestDto;
import main.application.user.dto.request.UserSignUpRequestDto;
import main.application.user.dto.response.UserResponseDto;
import main.application.user.exception.UserNotFoundException;
import main.application.user.external.UserRepository;
import main.application.user.internal.UserService;
import main.domain.user.User;

public class UserServiceRoot implements UserService {
    private final UserRepository repository;

    public UserServiceRoot(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserResponseDto signUp(UserSignUpRequestDto request) {
        User user = new User(null, request.getName(), request.getEmail(), request.getPassword(), null);
        User savedUser = repository.save(user);
        return new UserResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getPassword());
    }

    @Override
    public UserResponseDto logIn(UserLogInRequestDto request) {
        User user = repository.findByEmail(request.getEmail());
        
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            throw new UserNotFoundException("");
        }
        
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
    
}

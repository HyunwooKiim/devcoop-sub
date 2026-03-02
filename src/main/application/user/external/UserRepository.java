package main.application.user.external;

import main.domain.user.User;

public interface UserRepository {
    User save(User user);
    User findByEmail(String email);
}

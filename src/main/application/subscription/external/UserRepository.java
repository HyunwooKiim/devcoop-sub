package main.application.subscription.external;

import main.domain.user.internal.User;

public interface UserRepository {
    User findById(Long userId);
    void update(User user);
}

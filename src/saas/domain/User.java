package saas.domain;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final String userId;
    private final String email;
    private UserStatus status;

    private User(String userId, String email, UserStatus status) {
        this.userId = Objects.requireNonNull(userId);
        this.email = Objects.requireNonNull(email);
        this.status = Objects.requireNonNull(status);
    }

    public static User register(String email) {
        return new User(UUID.randomUUID().toString(), email, UserStatus.ACTIVE);
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public UserStatus getStatus() {
        return status;
    }
}

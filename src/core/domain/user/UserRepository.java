package core.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(String id);

    Optional<User> findByName(String name);

    List<User> findAll();

    void delete(String id);

    void update(User user);
}

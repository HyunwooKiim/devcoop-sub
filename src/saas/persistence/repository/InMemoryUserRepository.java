package saas.persistence.repository;

import saas.business.model.User;
import saas.business.repository.UserRepository;
import saas.database.InMemoryDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private final InMemoryDatabase database;

    public InMemoryUserRepository(InMemoryDatabase database) {
        this.database = database;
    }

    @Override
    public void save(User user) {
        database.userTable().put(user.getUserId(), user);
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(database.userTable().get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(database.userTable().values());
    }
}

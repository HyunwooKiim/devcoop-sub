package saas.persistence.repository;

import saas.business.model.Subscription;
import saas.business.model.SubscriptionStatus;
import saas.business.repository.SubscriptionRepository;
import saas.database.InMemoryDatabase;

import java.util.List;
import java.util.Optional;

public class InMemorySubscriptionRepository implements SubscriptionRepository {
    private final InMemoryDatabase database;

    public InMemorySubscriptionRepository(InMemoryDatabase database) {
        this.database = database;
    }

    @Override
    public void save(Subscription subscription) {
        database.subscriptionTable().put(subscription.getSubscriptionId(), subscription);
    }

    @Override
    public Optional<Subscription> findById(String subscriptionId) {
        return Optional.ofNullable(database.subscriptionTable().get(subscriptionId));
    }

    @Override
    public List<Subscription> findByUserId(String userId) {
        return database.subscriptionTable().values().stream()
                .filter(s -> s.getUserId().equals(userId))
                .toList();
    }

    @Override
    public boolean hasActivePaidSubscription(String userId) {
        return database.subscriptionTable().values().stream()
                .anyMatch(s -> s.getUserId().equals(userId)
                        && s.getPlanType().isPaid()
                        && s.getStatus() == SubscriptionStatus.ACTIVE);
    }
}

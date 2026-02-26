package infrastructure.repository;

import domain.subscription.Subscription;
import domain.subscription.SubscriptionRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemorySubscriptionRepository implements SubscriptionRepository {

    private final Map<String, Subscription> store = new ConcurrentHashMap<>();

    @Override
    public List<Subscription> findAllByUserId(String userId) {
        return store.values().stream()
                .filter(s -> s.getUserId().equals(userId)).toList();
    }

    @Override
    public Optional<Subscription> findById(String subscriptionId) {
        return Optional.ofNullable(store.get(subscriptionId));
    }

    @Override
    public Optional<Subscription> save(Subscription subscription) {
        store.put(subscription.getId(), subscription);
        return Optional.of(subscription);
    }

    @Override
    public void update(Subscription subscription) {
        store.put(subscription.getId(), subscription);
    }
}

package saas.repository.memory;

import saas.domain.Subscription;
import saas.domain.SubscriptionStatus;
import saas.repository.SubscriptionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemorySubscriptionRepository implements SubscriptionRepository {
    private final Map<String, Subscription> byId = new HashMap<>();

    @Override
    public void save(Subscription subscription) {
        byId.put(subscription.getSubscriptionId(), subscription);
    }

    @Override
    public Optional<Subscription> findById(String subscriptionId) {
        return Optional.ofNullable(byId.get(subscriptionId));
    }

    @Override
    public List<Subscription> findByUserId(String userId) {
        return byId.values().stream()
                .filter(s -> s.getUserId().equals(userId))
                .toList();
    }

    @Override
    public boolean hasActivePaidSubscription(String userId) {
        return byId.values().stream()
                .anyMatch(s -> s.getUserId().equals(userId)
                        && s.getPlanType().isPaid()
                        && s.getStatus() == SubscriptionStatus.ACTIVE);
    }
}

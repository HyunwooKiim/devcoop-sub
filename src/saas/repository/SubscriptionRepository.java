package saas.repository;

import saas.domain.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    void save(Subscription subscription);

    Optional<Subscription> findById(String subscriptionId);

    List<Subscription> findByUserId(String userId);

    boolean hasActivePaidSubscription(String userId);
}

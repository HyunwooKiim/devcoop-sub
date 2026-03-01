package core.domain.subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    List<Subscription> findAllByUserId(String userId);
    Optional<Subscription> findById(String subscriptionId);

    Optional<Subscription> save(Subscription subscription);
    void update(Subscription subscription);
}

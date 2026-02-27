package main.application.subscription.external;

import main.domain.subscription.internal.Subscription;

public interface SubscriptionRepository {
    void save(Subscription subscription);
    Subscription findById(Long id);
    void update(Subscription subscription);
}

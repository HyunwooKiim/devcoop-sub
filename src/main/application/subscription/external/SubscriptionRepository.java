package main.application.subscription.external;

import main.domain.subscription.Subscription;

public interface SubscriptionRepository {
    Subscription save(Subscription subscription);
    Subscription update(Subscription subscription);
    Subscription findByUserId(Long userId);
}

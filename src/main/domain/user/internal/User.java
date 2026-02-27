package main.domain.user.internal;

import main.domain.subscription.internal.Subscription;

public interface User {
    Long getId();
    Subscription getSubscription();
}

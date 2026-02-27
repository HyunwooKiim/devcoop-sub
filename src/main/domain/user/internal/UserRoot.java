package main.domain.user.internal;

import main.domain.subscription.internal.Subscription;

public class UserRoot implements User {
    private Long id;
    private Subscription subscription;

    public UserRoot(Long id, Subscription subscription) {
        this.id = id;
        this.subscription = subscription;
    }

    public Long getId() {
        return id;
    }

    public Subscription getSubscription() {
        return subscription;
    }

}

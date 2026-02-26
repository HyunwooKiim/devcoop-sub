package domain.user;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private List<Subscription> subscriptions = new ArrayList<>();

    public User(Long id) {
        this.id = id;
    }

    public Long subscribe(Long planId, Long price) {
        boolean hasActivePlan = this.subscriptions.stream().anyMatch(Subscription::isActive);

        if (hasActivePlan) {
            throw new  IllegalStateException("이미 활성화된 구독이 존재합니다.");
        }

        Long newSubscriptionId = (long) (Math.random() * 10000);
        Subscription newSubscription = new Subscription(newSubscriptionId, planId, price);
        this.subscriptions.add(newSubscription);

        return newSubscriptionId;
    }

    public void cancelSubscription(Long subscriptionId) {
        Subscription targetSubscription = this.subscriptions.stream().filter(sub -> sub.getId().equals(subscriptionId)).findFirst().orElseThrow(() -> new IllegalArgumentException("해당 구독 내역을 찾을 수 없습니다."));

        targetSubscription.cancel();
    }

    public void failSubscription(Long subscriptionId) {
        Subscription target = this.subscriptions.stream()
                .filter(sub -> sub.getId().equals(subscriptionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("구독 내역을 찾을 수 없습니다."));
        target.pastDue();
    }

    public Long getId() {
        return id;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }
}

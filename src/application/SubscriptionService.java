package application;

import domain.plan.Plan;
import domain.subscription.Subscription;
import domain.subscription.SubscriptionRepository;
import domain.subscription.SubscriptionStatus;

import java.util.List;

public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public String startSubscription(String userId, Plan selectedPlan) {
        List<Subscription> existingSubscription = subscriptionRepository.findAllByUserId(userId);

        boolean hasActivePlan = existingSubscription.stream()
                .anyMatch(s -> s.getStatus() == SubscriptionStatus.ACTIVE);

        if (hasActivePlan) {
            throw new IllegalStateException("이미 활성화된 구독이 존재합니다.");
        }

        Subscription newSubscription = new Subscription(userId, selectedPlan);
        subscriptionRepository.save(newSubscription);
        return newSubscription.getId();
    }
}

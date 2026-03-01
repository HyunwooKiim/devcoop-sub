package core.usecase.service.subscription;

import core.domain.plan.Plan;
import core.domain.subscription.Subscription;
import core.domain.subscription.SubscriptionRepository;
import core.domain.subscription.SubscriptionStatus;
import core.usecase.port.subscription.SubscriptionUseCase;

import java.util.List;

public class SubscriptionService implements SubscriptionUseCase {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
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

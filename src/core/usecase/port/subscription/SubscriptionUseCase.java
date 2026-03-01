package core.usecase.port.subscription;

import core.domain.plan.Plan;

public interface SubscriptionUseCase {
    String startSubscription(String userId, Plan selectedPlan);
}

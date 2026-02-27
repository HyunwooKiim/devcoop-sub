package main.application.subscription.external;

import main.domain.plan.internal.Plan;

public interface PlanRepository {
    Plan findById(Long planId);
    Plan findByType(String planType);
}

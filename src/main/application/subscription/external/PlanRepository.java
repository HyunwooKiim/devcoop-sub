package main.application.subscription.external;

import java.util.List;

import main.domain.plan.Plan;
import main.domain.plan.PlanType;

public interface PlanRepository {
    Plan save(Plan payment);
    Plan findById(Long planId);
    Plan findByPlanType(PlanType planType);
    List<Plan> findAll();
}

package saas.repository.memory;

import saas.domain.Plan;
import saas.domain.PlanType;
import saas.repository.PlanRepository;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryPlanRepository implements PlanRepository {
    private final Map<PlanType, Plan> plans = new EnumMap<>(PlanType.class);

    @Override
    public void save(Plan plan) {
        plans.put(plan.getPlanType(), plan);
    }

    @Override
    public Optional<Plan> findByType(PlanType planType) {
        return Optional.ofNullable(plans.get(planType));
    }

    @Override
    public List<Plan> findAll() {
        return new ArrayList<>(plans.values());
    }
}

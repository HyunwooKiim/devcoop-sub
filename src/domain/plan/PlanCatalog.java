package domain.plan;

import domain.common.valueobject.Money;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlanCatalog {
    private final Map<PlanName, Plan> plans = new EnumMap<>(PlanName.class);

    public PlanCatalog() {
        plans.put(PlanName.FREE, new Plan(PlanName.FREE, new Money(0L), 1000000000L));
        plans.put(PlanName.PRO, new Plan(PlanName.PRO, new Money(15000L), 30L));
        plans.put(PlanName.BUSINESS, new Plan(PlanName.BUSINESS, new Money(30000L), 30L));
    }

    public List<Plan> getAllPlans() {
        return new ArrayList<>(plans.values());
    }

    public Optional<Plan> findPlanByName(PlanName name) {
        return Optional.ofNullable(plans.get(name));
    }
}

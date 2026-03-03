package saas.persistence.repository;

import saas.business.model.Plan;
import saas.business.model.PlanType;
import saas.business.repository.PlanRepository;
import saas.database.InMemoryDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryPlanRepository implements PlanRepository {
    private final InMemoryDatabase database;

    public InMemoryPlanRepository(InMemoryDatabase database) {
        this.database = database;
    }

    @Override
    public void save(Plan plan) {
        database.planTable().put(plan.getPlanType(), plan);
    }

    @Override
    public Optional<Plan> findByType(PlanType planType) {
        return Optional.ofNullable(database.planTable().get(planType));
    }

    @Override
    public List<Plan> findAll() {
        return new ArrayList<>(database.planTable().values());
    }
}

package saas.business.repository;

import saas.business.model.Plan;
import saas.business.model.PlanType;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {
    void save(Plan plan);

    Optional<Plan> findByType(PlanType planType);

    List<Plan> findAll();
}

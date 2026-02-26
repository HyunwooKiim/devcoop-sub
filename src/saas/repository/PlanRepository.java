package saas.repository;

import saas.domain.Plan;
import saas.domain.PlanType;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {
    void save(Plan plan);

    Optional<Plan> findByType(PlanType planType);

    List<Plan> findAll();
}

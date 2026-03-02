package main.domain.plan;

import java.time.LocalDateTime;

public class Plan {
    private Long id;
    private PlanType planType;
    private Long charge;
    private Long period;
    private LocalDateTime createdAt;

    public Plan(Long id, PlanType planType, Long charge, Long period, LocalDateTime createdAt) {
        this.id = id;
        this.planType = planType;
        this.charge = charge;
        this.period = period;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public Long getCharge() {
        return charge;
    }

    public Long getPeriod() {
        return period;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

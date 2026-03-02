package main.application.subscription.dto;

import main.domain.plan.PlanType;

public class PlanInfoDto {
    private Long planId;
    private PlanType planType;
    private Long charge;
    private Long period;

    public PlanInfoDto(Long planId, PlanType planType, Long charge, Long period) {
        this.planId = planId;
        this.planType = planType;
        this.charge = charge;
        this.period = period;
    }

    public Long getPlanId() {
        return planId;
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
}

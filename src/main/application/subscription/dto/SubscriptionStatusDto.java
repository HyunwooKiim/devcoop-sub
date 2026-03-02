package main.application.subscription.dto;

import java.time.LocalDate;
import main.domain.plan.PlanType;

public class SubscriptionStatusDto {
    private PlanType currentPlan;
    private boolean isActive;
    private LocalDate endDate;
    private Long planCharge;
    private Long planPeriod;

    public SubscriptionStatusDto(PlanType currentPlan, boolean isActive, LocalDate endDate, Long planCharge, Long planPeriod) {
        this.currentPlan = currentPlan;
        this.isActive = isActive;
        this.endDate = endDate;
        this.planCharge = planCharge;
        this.planPeriod = planPeriod;
    }

    public PlanType getCurrentPlan() {
        return currentPlan;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getPlanCharge() {
        return planCharge;
    }

    public Long getPlanPeriod() {
        return planPeriod;
    }
}

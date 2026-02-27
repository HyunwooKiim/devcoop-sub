package main.application.subscription.dto;

import java.time.LocalDate;

public class SubscriptionResponse {
    private Long subscriptionId;
    private String status;
    private Long planId;
    private Long charge;
    private LocalDate startDate;
    private LocalDate endDate;

    public SubscriptionResponse(Long subscriptionId, String status, Long planId, 
                               Long charge, LocalDate startDate, LocalDate endDate) {
        this.subscriptionId = subscriptionId;
        this.status = status;
        this.planId = planId;
        this.charge = charge;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public String getStatus() {
        return status;
    }

    public Long getPlanId() {
        return planId;
    }

    public Long getCharge() {
        return charge;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}

package main.application.subscription.dto;

public class CreateSubscriptionRequest {
    private Long userId;
    private Long planId;

    public CreateSubscriptionRequest(Long userId, Long planId) {
        this.userId = userId;
        this.planId = planId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPlanId() {
        return planId;
    }
}

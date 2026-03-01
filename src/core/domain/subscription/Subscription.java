package core.domain.subscription;

import core.domain.plan.Plan;

import java.time.LocalDateTime;
import java.util.UUID;

public class Subscription {
    /**
     * variable
     */
    private String id;
    private String userId;
    private Plan planSnapShot;
    private SubscriptionStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;


    /**
     * constructor
     */
    public Subscription() {
        this.id = UUID.randomUUID().toString();
    }

    public Subscription(String userId, Plan plan) {
        this();
        this.userId = userId;
        this.planSnapShot = new Plan(plan.getName(), plan.getPrice(), plan.getPeriod());
        this.status = SubscriptionStatus.ACTIVE;
        this.startedAt = LocalDateTime.now();
        this.endAt = startedAt.plusDays(plan.getPeriod());
    }


    /**
     * getter
     */
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Plan getPlanSnapShot() {
        return planSnapShot;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }


    /**
     * method
     */
    public void activate() {
        this.status = SubscriptionStatus.ACTIVE;
    }

    public void pause() {
        this.status = SubscriptionStatus.PAUSED;
    }

    public void cancel() {
        if (this.status == SubscriptionStatus.CANCELED) {
            throw new IllegalStateException("이미 해지된 구독입니다.");
        }
        this.status = SubscriptionStatus.CANCELED;
    }

    public void markPastDue() {
        if (this.status == SubscriptionStatus.ACTIVE) {
            this.status = SubscriptionStatus.PAST_DUE;
        }
    }

    public void renew() {
        this.status = SubscriptionStatus.ACTIVE;
        this.endAt = this.endAt.plusDays(planSnapShot.getPeriod());
    }
}
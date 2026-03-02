package domain.user;

public class Subscription {
    private Long id;
    private Long planId;
    private Long price;
    private SubscriptionStatus status;

    public Subscription(Long id, Long planId, Long price) {
        this.id = id;
        this.planId = planId;
        this.price = price;
        this.status = SubscriptionStatus.ACTIVE;
    }

    public void cancel() {
        this.status = SubscriptionStatus.CANCELED;
    }

    public void pastDue() {
        this.status = SubscriptionStatus.PAST_DUE;
    }

    public boolean isActive() {
        return this.status ==  SubscriptionStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public Long getPlanId() {
        return planId;
    }

    public Long getPrice() {
        return price;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }
}

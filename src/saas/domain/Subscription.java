package saas.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Subscription {
    private final String subscriptionId;
    private final String userId;
    private final PlanType planType;

    private SubscriptionStatus status;
    private LocalDate startedAt;
    private LocalDateTime lastPaidAt;
    private long priceSnapshot;
    private int periodDaysSnapshot;

    public Subscription(String subscriptionId, String userId, PlanType planType) {
        this.subscriptionId = Objects.requireNonNull(subscriptionId, "subscriptionId must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.planType = Objects.requireNonNull(planType, "planType must not be null");
        this.status = SubscriptionStatus.PAUSED;
    }

    public static Subscription createNew(String userId, PlanType planType) {
        return new Subscription(UUID.randomUUID().toString(), userId, planType);
    }

    public void start(LocalDate startedAt, long priceSnapshot, int periodDaysSnapshot) {
        if (status == SubscriptionStatus.CANCELED) {
            throw new IllegalStateException("Canceled subscription cannot be started");
        }
        if (priceSnapshot < 0 || periodDaysSnapshot <= 0) {
            throw new IllegalArgumentException("Invalid plan snapshot");
        }

        this.startedAt = Objects.requireNonNull(startedAt, "startedAt must not be null");
        this.priceSnapshot = priceSnapshot;
        this.periodDaysSnapshot = periodDaysSnapshot;
        this.status = SubscriptionStatus.ACTIVE;
    }

    public void markPaid(LocalDateTime paidAt) {
        if (status == SubscriptionStatus.CANCELED) {
            throw new IllegalStateException("Canceled subscription cannot be paid");
        }
        this.lastPaidAt = Objects.requireNonNull(paidAt, "paidAt must not be null");
        this.status = SubscriptionStatus.ACTIVE;
    }

    public void markPastDue() {
        ensureNotCanceled();
        this.status = SubscriptionStatus.PAST_DUE;
    }

    public void pause() {
        ensureNotCanceled();
        this.status = SubscriptionStatus.PAUSED;
    }

    public void resume() {
        ensureNotCanceled();
        this.status = SubscriptionStatus.ACTIVE;
    }

    public void cancel() {
        this.status = SubscriptionStatus.CANCELED;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public String getUserId() {
        return userId;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public LocalDate getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getLastPaidAt() {
        return lastPaidAt;
    }

    public long getPriceSnapshot() {
        return priceSnapshot;
    }

    public int getPeriodDaysSnapshot() {
        return periodDaysSnapshot;
    }

    private void ensureNotCanceled() {
        if (status == SubscriptionStatus.CANCELED) {
            throw new IllegalStateException("Subscription is canceled");
        }
    }
}

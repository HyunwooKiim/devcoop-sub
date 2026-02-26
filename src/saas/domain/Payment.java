package saas.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Payment {
    private final String paymentId;
    private final String subscriptionId;
    private final PaymentType type;
    private final long amount;

    private PaymentStatus status;
    private LocalDateTime occurredAt;

    private Payment(String paymentId, String subscriptionId, PaymentType type, long amount) {
        this.paymentId = Objects.requireNonNull(paymentId, "paymentId must not be null");
        this.subscriptionId = Objects.requireNonNull(subscriptionId, "subscriptionId must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");

        if (amount < 0) {
            throw new IllegalArgumentException("amount must be >= 0");
        }
        this.amount = amount;
    }

    public static Payment createCharge(String subscriptionId, long amount) {
        return new Payment(UUID.randomUUID().toString(), subscriptionId, PaymentType.CHARGE, amount);
    }

    public static Payment createRefund(String subscriptionId, long amount) {
        return new Payment(UUID.randomUUID().toString(), subscriptionId, PaymentType.REFUND, amount);
    }

    public void markSucceeded(LocalDateTime occurredAt) {
        this.status = PaymentStatus.SUCCEEDED;
        this.occurredAt = Objects.requireNonNull(occurredAt, "occurredAt must not be null");
    }

    public void markFailed(LocalDateTime occurredAt) {
        this.status = PaymentStatus.FAILED;
        this.occurredAt = Objects.requireNonNull(occurredAt, "occurredAt must not be null");
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public PaymentType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}

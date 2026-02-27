package main.application.subscription.dto;

public class ProcessPaymentRequest {
    private Long subscriptionId;
    private boolean paymentSucceeded;

    public ProcessPaymentRequest(Long subscriptionId, boolean paymentSucceeded) {
        this.subscriptionId = subscriptionId;
        this.paymentSucceeded = paymentSucceeded;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public boolean isPaymentSucceeded() {
        return paymentSucceeded;
    }
}

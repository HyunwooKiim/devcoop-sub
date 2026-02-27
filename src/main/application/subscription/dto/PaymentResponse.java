package main.application.subscription.dto;

public class PaymentResponse {
    private Long subscriptionId;
    private String paymentStatus;
    private Long charge;
    private String subscriptionStatus;

    public PaymentResponse(Long subscriptionId, String paymentStatus, Long charge, String subscriptionStatus) {
        this.subscriptionId = subscriptionId;
        this.paymentStatus = paymentStatus;
        this.charge = charge;
        this.subscriptionStatus = subscriptionStatus;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Long getCharge() {
        return charge;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }
}

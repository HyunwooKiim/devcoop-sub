package domain.payment;

import java.util.UUID;

public class Payment {
    /**
     * variable
     */
    private String id;
    private String subscriptionId;
    private PaymentStatus status;


    /**
     * constructor
     */
    public Payment() {
        this.id = UUID.randomUUID().toString();
    }

    public Payment(String subscriptionId) {
        this();
        this.subscriptionId = subscriptionId;
        this.status = PaymentStatus.PENDING;
    }


    /**
     * getter
     */
    public String getId() {
        return id;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public PaymentStatus getStatus() {
        return status;
    }


    /**
     * method
     */
    public void purchase() {
        this.status = PaymentStatus.SUCCESS;
    }

    public void refuse() {
        this.status = PaymentStatus.FAILED;
    }

    public void refund() {
        this.status = PaymentStatus.REFUNDED;
    }
}

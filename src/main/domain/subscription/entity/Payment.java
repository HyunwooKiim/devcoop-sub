package main.domain.subscription.entity;

import main.domain.subscription.vo.PaymentStatus;

public class Payment {
    private Long id;
    private PaymentStatus status;
    private Long charge;

    public Payment(Long charge) {
        this.id = null;
        this.status = PaymentStatus.TRIED;
        this.charge = charge;
    }

    public void setPaid() {
        this.status = PaymentStatus.PAID;
    }

    public void setFailed() {
        this.status = PaymentStatus.FAILED;
    }

    public void setRefunded() {
        this.status = PaymentStatus.REFUNDED;
    }

    public boolean isPaid() {
        return status.isPaid();
    }

    public Long getId() {
        return id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Long getCharge() {
        return charge;
    }
}

package main.domain.payment;

import java.time.LocalDateTime;

public class Payment {
    private Long id;
    private PaymentStatus status;
    private Long charge;
    private LocalDateTime didAt;
    
    public Payment(Long id, PaymentStatus status, Long charge, LocalDateTime didAt) {
        this.id = id;
        this.status = status;
        this.charge = charge;
        this.didAt = didAt;
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

    public LocalDateTime getDidAt() {
        return didAt;
    }

    public boolean isPaid() {
        return status == PaymentStatus.PAID;
    }
}

package main.domain.payment;

public enum PaymentStatus {
    PAID("PAID"), 
    TRYING("TRYING"), 
    FAILED("FAILED"), 
    REFUNDED("REFUNDED");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

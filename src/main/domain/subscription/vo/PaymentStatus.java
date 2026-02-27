package main.domain.subscription.vo;

public enum PaymentStatus {
    PAID("PAID"),
    TRIED("TRIED"),
    FAILED("FAILED"),
    REFUNDED("REFUNDED");

    private final String value;

    private PaymentStatus(String value) {
        this.value = value;
    }

    public boolean isPaid() {
        return this.value == PaymentStatus.PAID.name();
    }
}

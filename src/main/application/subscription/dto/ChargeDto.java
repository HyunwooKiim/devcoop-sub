package main.application.subscription.dto;

public class ChargeDto {
    private Long amount;
    private boolean shouldCharge;
    private String reason;

    public ChargeDto(Long amount, boolean shouldCharge, String reason) {
        this.amount = amount;
        this.shouldCharge = shouldCharge;
        this.reason = reason;
    }

    public Long getAmount() {
        return amount;
    }

    public boolean isShouldCharge() {
        return shouldCharge;
    }

    public String getReason() {
        return reason;
    }
}

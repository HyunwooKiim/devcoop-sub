package domain.payment;

public class Payment {
    private Long id;
    private Long userId;
    private Long subscriptionId;
    private Long price;
    private PaymentStatus status;

    public Payment(Long id, Long userId, Long subscriptionId, Long price) {
        this.id = id;
        this.userId = userId;
        this.subscriptionId = subscriptionId;
        this.price = price;
        this.status = PaymentStatus.PENDING;
    }

    public void complete() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("대기 중인 결제만 완료할 수 있습니다.");
        }
        this.status = PaymentStatus.SUCCESS;
    }

    public void fail() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("대기 중인 결제만 실패할 수 있습니다.");
        }
        this.status = PaymentStatus.FAILED;
    }

    public void refund() {
        if (this.status != PaymentStatus.SUCCESS) {
            throw new IllegalStateException("성공한 결제만 환불할 수 있습니다.");
        }
        this.status = PaymentStatus.REFUNDED;
    }

    public Long getId() { return id; }

    public Long getUserId() {
        return userId;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Long getPrice() {
        return price;
    }
}

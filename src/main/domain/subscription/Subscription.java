package main.domain.subscription;

import java.time.LocalDate;

import main.domain.payment.Payment;
import main.domain.payment.PaymentStatus;
import main.domain.plan.Plan;

public class Subscription {
    private Long id;
    private Plan plan;
    private Payment payment;
    private LocalDate endDate;

    public Subscription(Long id, Plan plan, Payment payment) {
        this.id = id;
        this.plan = plan;
        this.payment = payment;
        this.endDate = null;
    }

    public Subscription(Long id, Plan plan, Payment payment, LocalDate endDate) {
        this.id = id;
        this.plan = plan;
        this.payment = payment;
        this.endDate = endDate;
    }
    
    public Long getId() {
        return id;
    }

    public Plan getPlan() {
        return plan;
    }

    public Payment getPayment() {
        return payment;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * 구독이 활성화되어 있는지 확인
     * - Payment가 PAID 상태
     * - Payment의 금액이 현재 Plan의 금액과 일치
     * - 현재 날짜가 endDate 이전(또는 endDate가 null인 경우도 확인 필요)
     */
    public boolean isActive() {
        if (payment == null || plan == null) {
            return false;
        }
        
        // Payment가 PAID 상태이고 금액이 일치해야 함
        boolean paymentValid = payment.isPaid() && payment.getCharge().equals(plan.getCharge());
        
        // endDate가 없거나 현재 날짜가 endDate 이전이어야 함
        if (endDate == null) {
            return paymentValid && payment.getStatus() == PaymentStatus.PAID;
        }
        
        return paymentValid && LocalDate.now().isBefore(endDate) || LocalDate.now().isEqual(endDate);
    }

    /**
     * 구독 활성화 시간 업데이트 (결제 완료 후 호출)
     */
    public void activateSubscription() {
        if (payment != null && payment.isPaid() && payment.getCharge().equals(plan.getCharge())) {
            LocalDate paymentDate = payment.getDidAt().toLocalDate();
            this.endDate = paymentDate.plusDays(plan.getPeriod());
        }
    }

    /**
     * 플랜 변경
     */
    public void changePlan(Plan newPlan) {
        this.plan = newPlan;
    }

    /**
     * 결제 정보 업데이트
     */
    public void updatePayment(Payment newPayment) {
        this.payment = newPayment;
    }
}

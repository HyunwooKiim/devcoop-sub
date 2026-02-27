package main.domain.subscription.internal;

import java.time.LocalDate;

import main.domain.plan.internal.Plan;
import main.domain.subscription.entity.Payment;
import main.domain.subscription.vo.Status;

public class SubscriptionRoot implements Subscription {
    private Long id;
    private Status status;
    private Plan plan;
    private Long charge;
    private Long period;
    private LocalDate startDate;
    private LocalDate endDate;
    private Payment payment;

    public SubscriptionRoot(Long id, Status status, Plan plan, Long charge, Long period,
                            LocalDate startDate, LocalDate endDate, Payment payment) {
        this.id = id;
        this.status = status;
        this.plan = plan;
        this.charge = charge;
        this.period = period;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payment = payment;
    }
    
    @Override
    public void changePlan(Plan plan) {
        if (this.plan.equals(plan)) return;
        this.plan = plan;
        this.charge = plan.getCharge();
        this.period = plan.getPeriod();
        this.payment = new Payment(this.charge);
        this.status = Status.DEACTIVATED;

        if (this.plan.isFree()) {
            payPayment();
        }
    }

    @Override
    public Long calculateCharge() {
        if (payment.isPaid()) return 0L;
        return payment.getCharge();
    }

    @Override
    public void payPayment() {
        payment.setPaid();
        status = Status.ACTIVATED;
        startDate = LocalDate.now();
        endDate = startDate.plusDays(period);
    }

    @Override
    public void failPayment() {
        payment.setFailed();
        status = Status.PAST_DUE;
    }

    @Override
    public void refundPayment() {
        payment.setRefunded();
        status = Status.DEACTIVATED;
    }

    @Override
    public boolean isActivated() {
        if (endDate.isBefore(LocalDate.now())) status = Status.DEACTIVATED;
        if (!status.isActivated()) return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public Long getCharge() {
        return charge;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public Long getPeriod() {
        return period;
    }

    @Override
    public Plan getPlan() {
        return plan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    
    public Status getStatus() {
        return status;
    }

}

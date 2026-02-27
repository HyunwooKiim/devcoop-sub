package main.domain.subscription.internal;

import java.time.LocalDate;

import main.domain.plan.internal.Plan;
import main.domain.subscription.entity.Payment;
import main.domain.subscription.vo.Status;

public interface Subscription {
    void changePlan(Plan plan);
    Long calculateCharge();
    void payPayment();
    void failPayment();
    void refundPayment();
    boolean isActivated();
    Plan getPlan();
    Long getId();
    Status getStatus();
    Long getCharge();
    Long getPeriod();
    LocalDate getStartDate();
    LocalDate getEndDate();
    Payment getPayment();
}

package application;

import domain.payment.Payment;
import domain.plan.Plan;
import domain.user.User;

import java.util.List;

public interface SubscriptionUseCase {
    List<Plan> getPlans();
    User getSubscriptionInfo(Long userId);
    List<Payment> getPendingPayments();
    Payment requestSubscription(Long userId, Long planId);
    void processPaymentSuccess(Long paymentId);
    void processPaymentFailure(Long paymentId);
    void cancelSubscription(Long userId, Long subscriptionId);
}

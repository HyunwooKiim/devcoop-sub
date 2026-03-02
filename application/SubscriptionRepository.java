package application;

import domain.payment.Payment;
import domain.plan.Plan;
import domain.user.User;

import java.util.Collection;
import java.util.Optional;

public interface SubscriptionRepository {
    Optional<User> findUserById(Long userId);
    Optional<Plan> findPlanById(Long planId);
    Collection<Plan> findAllPlans();

    Optional<Payment> findPaymentById(Long paymentId);
    Collection<Payment> findAllPayments();
    Payment createAndSavePayment(Long userId, Long subscriptionId, Long price);
}

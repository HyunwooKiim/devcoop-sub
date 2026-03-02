package infrastructure;

import application.SubscriptionRepository;
import domain.payment.Payment;
import domain.plan.Plan;
import domain.plan.PlanType;
import domain.user.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryRepository implements SubscriptionRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Plan> plans = new HashMap<>();
    private final Map<Long, Payment> payments = new HashMap<>();

    private Long paymentId = 1L;

    public void initData() {
        plans.put(1L, new Plan(1L, PlanType.FREE, 0L, 100L));
        plans.put(2L, new Plan(2L, PlanType.PRO, 9900L, 1000L));
        plans.put(3L, new Plan(3L, PlanType.BUSINESS, 29900L, 10000L));

        Long currentUserId = 1L;
        users.put(currentUserId, new User(currentUserId));
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public Optional<Plan> findPlanById(Long planId) {
        return Optional.ofNullable(plans.get(planId));
    }

    @Override
    public Collection<Plan> findAllPlans() {
        return plans.values();
    }

    @Override
    public Optional<Payment> findPaymentById(Long paymentId) {
        return Optional.ofNullable(payments.get(paymentId));
    }

    @Override
    public Collection<Payment> findAllPayments() {
        return payments.values();
    }

    @Override
    public Payment createAndSavePayment(Long userId, Long subscriptionId, Long price) {
        Long newPaymentId = this.paymentId++;
        Payment payment = new Payment(newPaymentId, userId, subscriptionId, price);
        payments.put(newPaymentId, payment);
        return payment;
    }

    public User getInitialUser() {
        return users.get(1L);
    }
}
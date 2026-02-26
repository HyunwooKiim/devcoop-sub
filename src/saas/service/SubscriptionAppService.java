package saas.service;

import saas.domain.Payment;
import saas.domain.Plan;
import saas.domain.PlanType;
import saas.domain.Subscription;
import saas.domain.User;
import saas.repository.PaymentRepository;
import saas.repository.PlanRepository;
import saas.repository.SubscriptionRepository;
import saas.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SubscriptionAppService {
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;

    public SubscriptionAppService(
            UserRepository userRepository,
            PlanRepository planRepository,
            SubscriptionRepository subscriptionRepository,
            PaymentRepository paymentRepository
    ) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.paymentRepository = paymentRepository;
    }

    public User registerUser(String email) {
        User user = User.register(email);
        userRepository.save(user);
        return user;
    }

    public Subscription subscribe(String userId, PlanType planType) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Plan plan = planRepository.findByType(planType)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found: " + planType));

        if (planType.isPaid() && subscriptionRepository.hasActivePaidSubscription(userId)) {
            throw new IllegalStateException("User already has an active paid subscription");
        }

        Subscription subscription = Subscription.createNew(userId, planType);
        subscription.start(LocalDate.now(), plan.getPrice(), plan.getPeriodDays());
        subscriptionRepository.save(subscription);
        return subscription;
    }

    public Payment charge(String subscriptionId, boolean success) {
        Subscription subscription = getSubscription(subscriptionId);
        Payment payment = Payment.createCharge(subscriptionId, subscription.getPriceSnapshot());

        if (success) {
            payment.markSucceeded(LocalDateTime.now());
            subscription.markPaid(payment.getOccurredAt());
        } else {
            payment.markFailed(LocalDateTime.now());
            subscription.markPastDue();
        }

        paymentRepository.save(payment);
        subscriptionRepository.save(subscription);
        return payment;
    }

    public Payment refund(String subscriptionId, long amount) {
        Subscription subscription = getSubscription(subscriptionId);
        Payment payment = Payment.createRefund(subscriptionId, amount);
        payment.markSucceeded(LocalDateTime.now());

        paymentRepository.save(payment);
        subscriptionRepository.save(subscription);
        return payment;
    }

    public void pause(String subscriptionId) {
        Subscription subscription = getSubscription(subscriptionId);
        subscription.pause();
        subscriptionRepository.save(subscription);
    }

    public void resume(String subscriptionId) {
        Subscription subscription = getSubscription(subscriptionId);
        subscription.resume();
        subscriptionRepository.save(subscription);
    }

    public void cancel(String subscriptionId) {
        Subscription subscription = getSubscription(subscriptionId);
        subscription.cancel();
        subscriptionRepository.save(subscription);
    }

    public Subscription getSubscription(String subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + subscriptionId));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<Plan> getPlans() {
        return planRepository.findAll();
    }

    public List<Subscription> getSubscriptionsByUser(String userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsBySubscription(String subscriptionId) {
        return paymentRepository.findBySubscriptionId(subscriptionId);
    }
}

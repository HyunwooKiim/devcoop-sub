package application;

import domain.payment.Payment;
import domain.payment.PaymentStatus;
import domain.plan.Plan;
import domain.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionService implements SubscriptionUseCase {

    private final SubscriptionRepository repository;

    public SubscriptionService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Plan> getPlans() {
        return new ArrayList<>(repository.findAllPlans());
    }

    @Override
    public User getSubscriptionInfo(Long userId) {
        return repository.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }

    @Override
    public List<Payment> getPendingPayments() {
        return repository.findAllPayments().stream()
                .filter(p -> p.getStatus() == PaymentStatus.PENDING)
                .collect(Collectors.toList());
    }

    @Override
    public Payment requestSubscription(Long userId, Long planId) {
        User user = repository.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        Plan plan = repository.findPlanById(planId)
                .orElseThrow(() -> new IllegalArgumentException("플랜이 존재하지 않습니다."));

        Long subscriptionId = user.subscribe(planId, plan.getPrice());

        return repository.createAndSavePayment(userId, subscriptionId, plan.getPrice());
    }

    @Override
    public void processPaymentSuccess(Long paymentId) {
        Payment payment = repository.findPaymentById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 내역이 없습니다."));
        payment.complete();
    }

    @Override
    public void processPaymentFailure(Long paymentId) {
        Payment payment = repository.findPaymentById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 내역이 없습니다."));
        payment.fail();

        User targetUser = repository.findUserById(payment.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("결제 실패 처리 중 유저를 찾을 수 없습니다."));
        targetUser.failSubscription(payment.getSubscriptionId());
    }



    @Override
    public void cancelSubscription(Long userId, Long subscriptionId) {
        User user = repository.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        user.cancelSubscription(subscriptionId);
    }
}

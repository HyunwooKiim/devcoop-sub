package service;

import domain.payment.Payment;
import domain.plan.Plan;
import repository.MemoryRepository;
import domain.user.User;

public class SubscriptionService {
    public Payment requestSubscription(Long userId, Long planId) {
        User user = MemoryRepository.users.get(userId);
        Plan plan = MemoryRepository.plans.get(planId);

        if (user == null || plan == null) {
            throw new IllegalArgumentException("유저 또는 플랜이 존재하지 않습니다.");
        }

        Long subscriptionId = user.subscribe(planId, plan.getPrice());

        Long paymentId = MemoryRepository.generatePaymentId();
        Payment payment = new Payment(paymentId, userId, subscriptionId, plan.getPrice());
        MemoryRepository.payments.put(paymentId, payment);

        return payment;
    }

    public void paymentSuccess(Long paymentId) {
        Payment payment = MemoryRepository.payments.get(paymentId);
        if (payment == null) throw new IllegalArgumentException("결제 내역이 없습니다.");

        payment.complete();
    }

    public void paymentFail(Long paymentId) {
        Payment payment = MemoryRepository.payments.get(paymentId);
        if (payment == null) throw new IllegalArgumentException("결제 내역이 없습니다.");

        payment.fail();

        User targetUser = MemoryRepository.users.get(payment.getUserId());
        targetUser.failSubscription(payment.getSubscriptionId());
    }

    public void cancelSubscription(Long userId, Long subscriptionId) {
        User user = MemoryRepository.users.get(userId);
        user.cancelSubscription(subscriptionId);
    }
}

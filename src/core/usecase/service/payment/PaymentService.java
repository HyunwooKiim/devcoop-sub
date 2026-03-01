package core.usecase.service.payment;

import core.domain.audit.AuditLog;
import core.domain.audit.AuditRepository;
import core.domain.common.valueobject.money.Money;
import core.domain.payment.Payment;
import core.domain.payment.PaymentRepository;
import core.domain.subscription.Subscription;
import core.domain.subscription.SubscriptionRepository;
import core.domain.user.User;
import core.domain.user.UserRepository;
import core.usecase.port.payment.PaymentUseCase;
import infrastructure.logger.AppLogger;

public class PaymentService implements PaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final AuditRepository auditRepository;

    public PaymentService(PaymentRepository paymentRepository, SubscriptionRepository subscriptionRepository,
            UserRepository userRepository, AuditRepository auditRepository) {
        this.paymentRepository = paymentRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.auditRepository = auditRepository;
    }

    @Override
    public void processPayment(String subscriptionId) {
        AppLogger.info("PaymentService", "결제 프로세스 시작: SubID " + subscriptionId);
        Subscription sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        User user = userRepository.findById(sub.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Money amount = sub.getPlanSnapShot().getPrice();
        Payment payment = new Payment(subscriptionId);

        try {
            user.withdraw(amount);
            userRepository.update(user);

            payment.purchase();
            sub.activate();
            AppLogger.info("PaymentService", "결제 성공: " + user.getName() + " - " + amount);
            auditRepository.save(new AuditLog(user.getId(), "PAYMENT_SUCCESS", "결제 성공: " + amount));
        } catch (Exception e) {
            payment.refuse();
            sub.markPastDue();
            AppLogger.error("PaymentService", "결제 실패: " + user.getName(), e);
            auditRepository.save(new AuditLog(user.getId(), "PAYMENT_FAILED", "결제 실패: " + e.getMessage()));
        }

        subscriptionRepository.update(sub);
        paymentRepository.save(payment);
    }
}

package infrastructure.config;

import adapter.out.persistence.MemoryAuditRepository;
import adapter.out.persistence.MemoryPaymentRepository;
import adapter.out.persistence.MemorySubscriptionRepository;
import adapter.out.persistence.MemoryUserRepository;
import core.usecase.port.payment.PaymentUseCase;
import core.usecase.port.subscription.SubscriptionUseCase;
import core.usecase.port.user.UserUseCase;
import core.usecase.service.payment.PaymentService;
import core.usecase.service.subscription.SubscriptionService;
import core.usecase.service.user.UserService;
import core.domain.audit.AuditRepository;
import core.domain.payment.PaymentRepository;
import core.domain.subscription.SubscriptionRepository;
import core.domain.user.UserRepository;

public class AppConfig {
    /**
     * variable
     */
    private static final SubscriptionRepository subscriptionRepository = new MemorySubscriptionRepository();
    private static final PaymentRepository paymentRepository = new MemoryPaymentRepository();
    private static final UserRepository userRepository = new MemoryUserRepository();
    private static final AuditRepository auditRepository = new MemoryAuditRepository();

    private static final SubscriptionUseCase subscriptionService = new SubscriptionService(subscriptionRepository);
    private static final PaymentUseCase paymentService = new PaymentService(paymentRepository, subscriptionRepository, userRepository, auditRepository);
    private static final UserUseCase userService = new UserService(userRepository, auditRepository);


    /**
     * getter
     */
    public static PaymentUseCase getPaymentUseCase() {
        return paymentService;
    }

    public static SubscriptionUseCase getSubscriptionUseCase() {
        return subscriptionService;
    }

    public static UserUseCase getUserUseCase() {
        return userService;
    }
}

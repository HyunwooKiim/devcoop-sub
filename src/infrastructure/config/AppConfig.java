package infrastructure.config;

import application.PaymentService;
import application.SubscriptionService;
import application.UserService;
import domain.audit.AuditRepository;
import domain.payment.PaymentRepository;
import domain.subscription.SubscriptionRepository;
import domain.user.UserRepository;

public class AppConfig {
    /**
     * variable
     */
    private static final SubscriptionRepository subscriptionRepository = new infrastructure.repository.MemorySubscriptionRepository();
    private static final PaymentRepository paymentRepository = new infrastructure.repository.MemoryPaymentRepository();
    private static final UserRepository userRepository = new infrastructure.repository.MemoryUserRepository();
    private static final AuditRepository auditRepository = new infrastructure.repository.MemoryAuditRepository();

    private static final SubscriptionService subscriptionService = new SubscriptionService(subscriptionRepository);
    private static final PaymentService paymentService = new PaymentService(paymentRepository, subscriptionRepository, userRepository, auditRepository);
    private static final UserService userService = new UserService(userRepository, auditRepository);


    /**
     * getter
     */
    public static PaymentService getPaymentService() {
        return paymentService;
    }

    public static SubscriptionService getSubscriptionService() {
        return subscriptionService;
    }

    public static UserService getUserService() {
        return userService;
    }
}

package main.application.subscription.external;

import main.domain.payment.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
    Payment findById(Long paymentId);
}

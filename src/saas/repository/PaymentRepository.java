package saas.repository;

import saas.domain.Payment;

import java.util.List;

public interface PaymentRepository {
    void save(Payment payment);

    List<Payment> findAll();

    List<Payment> findBySubscriptionId(String subscriptionId);
}

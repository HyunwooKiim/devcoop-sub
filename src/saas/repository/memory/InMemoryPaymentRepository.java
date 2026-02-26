package saas.repository.memory;

import saas.domain.Payment;
import saas.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPaymentRepository implements PaymentRepository {
    private final List<Payment> payments = new ArrayList<>();

    @Override
    public void save(Payment payment) {
        payments.add(payment);
    }

    @Override
    public List<Payment> findAll() {
        return new ArrayList<>(payments);
    }

    @Override
    public List<Payment> findBySubscriptionId(String subscriptionId) {
        return payments.stream()
                .filter(payment -> payment.getSubscriptionId().equals(subscriptionId))
                .toList();
    }
}

package saas.persistence.repository;

import saas.business.model.Payment;
import saas.business.repository.PaymentRepository;
import saas.database.InMemoryDatabase;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPaymentRepository implements PaymentRepository {
    private final InMemoryDatabase database;

    public InMemoryPaymentRepository(InMemoryDatabase database) {
        this.database = database;
    }

    @Override
    public void save(Payment payment) {
        database.paymentTable().add(payment);
    }

    @Override
    public List<Payment> findAll() {
        return new ArrayList<>(database.paymentTable());
    }

    @Override
    public List<Payment> findBySubscriptionId(String subscriptionId) {
        return database.paymentTable().stream()
                .filter(payment -> payment.getSubscriptionId().equals(subscriptionId))
                .toList();
    }
}

package infrastructure.repository;

import domain.payment.Payment;
import domain.payment.PaymentRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryPaymentRepository implements PaymentRepository {

    private final Map<String, Payment> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Payment> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Payment> save(Payment payment) {
        store.put(payment.getId(), payment);
        return Optional.of(payment);
    }
}

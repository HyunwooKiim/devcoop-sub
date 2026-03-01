package core.domain.payment;

import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(String id);

    Optional<Payment> save(Payment payment);
}

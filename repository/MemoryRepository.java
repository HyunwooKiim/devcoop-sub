package repository;

import domain.payment.Payment;
import domain.plan.Plan;
import domain.user.User;

import java.util.HashMap;
import java.util.Map;

public class MemoryRepository {
    public static final Map<Long, User> users = new HashMap<>();
    public static final Map<Long, Plan> plans = new HashMap<>();
    public static final Map<Long, Payment> payments = new HashMap<>();

    private static Long paymentId = 1L;

    public static Long generatePaymentId() {
        return paymentId++;
    }
}
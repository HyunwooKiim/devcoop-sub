package saas.database;

import saas.business.model.Payment;
import saas.business.model.Plan;
import saas.business.model.PlanType;
import saas.business.model.Subscription;
import saas.business.model.User;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDatabase {
    private final Map<String, User> userTable = new HashMap<>();
    private final Map<PlanType, Plan> planTable = new EnumMap<>(PlanType.class);
    private final Map<String, Subscription> subscriptionTable = new HashMap<>();
    private final List<Payment> paymentTable = new ArrayList<>();

    public Map<String, User> userTable() {
        return userTable;
    }

    public Map<PlanType, Plan> planTable() {
        return planTable;
    }

    public Map<String, Subscription> subscriptionTable() {
        return subscriptionTable;
    }

    public List<Payment> paymentTable() {
        return paymentTable;
    }
}

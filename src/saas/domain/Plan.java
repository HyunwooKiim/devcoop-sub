package saas.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Plan {
    private final PlanType planType;
    private final long currentPrice;
    private final int billingPeriodDays;
    private final Map<String, Integer> limits;

    public Plan(PlanType planType, long currentPrice, int billingPeriodDays, Map<String, Integer> limits) {
        this.planType = Objects.requireNonNull(planType, "planType must not be null");
        if (currentPrice < 0) {
            throw new IllegalArgumentException("currentPrice must be >= 0");
        }
        if (billingPeriodDays <= 0) {
            throw new IllegalArgumentException("billingPeriodDays must be > 0");
        }

        this.currentPrice = currentPrice;
        this.billingPeriodDays = billingPeriodDays;
        this.limits = Collections.unmodifiableMap(new HashMap<>(Objects.requireNonNull(limits, "limits must not be null")));
    }

    public PlanType getPlanType() {
        return planType;
    }

    public long getPrice() {
        return currentPrice;
    }

    public int getPeriodDays() {
        return billingPeriodDays;
    }

    public Map<String, Integer> getLimits() {
        return limits;
    }
}

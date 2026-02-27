package main.domain.plan.internal;

import main.domain.plan.vo.PlanType;

public class PlanRoot implements Plan {
    private Long id;
    private PlanType type;
    private Long charge;
    private Long period;

    public PlanRoot(Long id, PlanType type, Long charge, Long period) {
        this.id = id;
        this.type = type;
        this.charge = charge;
        this.period = period;
    }

    @Override
    public boolean isFree() {
        return type.isFree();
    }

    @Override
    public boolean isPro() {
        return type.isPro();
    }

    @Override
    public boolean isBusiness() {
        return type.isBusiness();
    }

    @Override
    public Long getCharge() {
        return charge;
    }

    @Override
    public Long getPeriod() {
        return period;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type.name();
    }
}

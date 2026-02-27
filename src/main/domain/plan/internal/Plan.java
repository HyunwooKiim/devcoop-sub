package main.domain.plan.internal;

public interface Plan {
    public boolean isFree();
    public boolean isPro();
    public boolean isBusiness();
    public Long getCharge();
    public Long getPeriod();
    public String getType();
}

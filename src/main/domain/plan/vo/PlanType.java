package main.domain.plan.vo;

public enum PlanType {
    FREE("FREE"),
    PRO("PRO"),
    BUSINESS("BUSINESS");

    private final String value;

    private PlanType(String value) {
        this.value = value;
    }

    public boolean isFree() {
        return this.value == PlanType.FREE.name();
    }

    public boolean isPro() {
        return this.value == PlanType.PRO.name();
    }

    public boolean isBusiness() {
        return this.value == PlanType.BUSINESS.name();
    }

    public String getValue() {
        return value;
    }
}

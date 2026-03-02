package main.domain.plan;

public enum PlanType {
    FREE("FREE"), 
    PRO("PRO"), 
    BUSINESS("BUSINESS");

    private final String value;

    PlanType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package saas.domain;

public enum PlanType {
    FREE,
    PRO,
    BUSINESS;

    public boolean isPaid() {
        return this != FREE;
    }
}

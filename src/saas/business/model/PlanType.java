package saas.business.model;

public enum PlanType {
    FREE,
    PRO,
    BUSINESS;

    public boolean isPaid() {
        return this != FREE;
    }
}

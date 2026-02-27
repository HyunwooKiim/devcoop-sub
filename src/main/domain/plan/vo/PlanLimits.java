package main.domain.plan.vo;

public class PlanLimits {
    private Long maxProjects;
    private Long maxUsers;
    private Long maxStorage;
    private boolean hasAdvancedAnalytics;
    private boolean hasPrioritySupport;

    public PlanLimits(Long maxProjects, Long maxUsers, Long maxStorage, 
                      boolean hasAdvancedAnalytics, boolean hasPrioritySupport) {
        this.maxProjects = maxProjects;
        this.maxUsers = maxUsers;
        this.maxStorage = maxStorage;
        this.hasAdvancedAnalytics = hasAdvancedAnalytics;
        this.hasPrioritySupport = hasPrioritySupport;
    }

    public Long getMaxProjects() {
        return maxProjects;
    }

    public Long getMaxUsers() {
        return maxUsers;
    }

    public Long getMaxStorage() {
        return maxStorage;
    }

    public boolean hasAdvancedAnalytics() {
        return hasAdvancedAnalytics;
    }

    public boolean hasPrioritySupport() {
        return hasPrioritySupport;
    }
}

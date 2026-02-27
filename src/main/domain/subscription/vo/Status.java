package main.domain.subscription.vo;

public enum Status {
    ACTIVATED("ACTIVATED"),
    DEACTIVATED("DEACTIVATED"),
    PAST_DUE("PAST_DUE"),
    PAUSE("PAUSE");

    private final String value;

    private Status(String value) {
        this.value = value;
    }

    public boolean isActivated() {
        return this.value == Status.ACTIVATED.name();
    }
}

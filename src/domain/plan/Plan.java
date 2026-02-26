package domain.plan;

import java.util.UUID;
import domain.common.valueobject.Money;

public class Plan {
    /**
     * variable
     */
    private String id;
    private PlanName name;
    private Money price;
    private Long period; // Day



    /**
     * constructor
     */
    public Plan() {
        this.id = UUID.randomUUID().toString();
    }

    public Plan(PlanName name, Money price, Long period) {
        this();
        this.name = name;
        this.price = price;
        this.period = period;
    }



    /**
     * getter
     */
    public String getId() {
        return id;
    }

    public PlanName getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public Long getPeriod() {
        return period;
    }
}

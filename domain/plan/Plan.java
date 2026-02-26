package domain.plan;

public class Plan {
    private Long id;
    private PlanType type;
    private Long price;
    private Long limits;

    public Plan(Long id, PlanType type, Long price, Long limits) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.limits = limits;
    }

    public void changePrice(Long price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 음수가 될 수 없습니다.");
        }
        this.price = price;
    }

    public Long getId() { return id; }
    public Long getPrice() { return price; }
    public PlanType getType() { return type; }
}

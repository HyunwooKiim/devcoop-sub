package core.domain.common.valueobject.money;

import java.util.Objects;

public final class Money {
    /**
     * variable
     */
    private final Long amount;


    /**
     * constructor
     */
    public Money(Long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("금액은 0보다 작을 수 없습니다.");
        }
        this.amount = amount;
    }


    /**
     * method
     */
    public static Money zero() {
        return new Money(0L);
    }

    public Long getAmount() {
        return amount;
    }

    public Money add(Money other) {
        return new Money(this.amount + other.amount);
    }

    public Money subtract(Money other) {
        if (this.amount < other.amount) {
            throw new IllegalStateException("잔액이 부족하여 뺄 수 없습니다.");
        }
        return new Money(this.amount - other.amount);
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return this.amount >= other.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return amount + "원";
    }
}

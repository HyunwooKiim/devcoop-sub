package core.domain.user;

import core.domain.common.valueobject.money.Money;
import java.util.UUID;

public class User {
    /**
     * variable
     */
    private String id;
    private String name;
    private String password;
    private Money balance;


    /**
     * constructor
     */
    public User() {
        this.id = UUID.randomUUID().toString();
        this.balance = Money.zero();
    }

    public User(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }


    /**
     * getter
     */
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Money getBalance() {
        return balance;
    }


    /**
     * method
     */
    public void deposit(Money amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(Money amount) {
        this.balance = this.balance.subtract(amount);
    }
}

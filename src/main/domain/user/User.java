package main.domain.user;

import main.domain.subscription.Subscription;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Subscription subscription;

    public User(Long id, String name, String email, String password, Subscription subscription) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.subscription = subscription;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Subscription getSubscription() {
        return subscription;
    }
    
}

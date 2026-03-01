package core.usecase.port.user;

import core.domain.audit.AuditLog;
import core.domain.common.valueobject.money.Money;
import core.domain.user.User;

import java.util.List;

public interface UserUseCase {
    // log
    List<AuditLog> getAuditLogs(String userId);

    // account management
    String signUp(String name, String password);
    User login(String name, String password);
    void withdraw(String userId);

    // money management
    Money getBalance(String userId);
    void deposit(String userId, Money amount);
    void withdrawMoney(String userId, Money amount);
}

package core.usecase.service.user;

import core.domain.audit.AuditLog;
import core.domain.audit.AuditRepository;
import core.domain.common.valueobject.money.Money;
import core.domain.user.User;
import core.domain.user.UserRepository;
import core.usecase.port.user.UserUseCase;
import infrastructure.logger.AppLogger;

import java.util.List;

public class UserService implements UserUseCase {

    private final UserRepository userRepository;
    private final AuditRepository auditRepository;

    public UserService(UserRepository userRepository, AuditRepository auditRepository) {
        this.userRepository = userRepository;
        this.auditRepository = auditRepository;
    }

    @Override
    public List<AuditLog> getAuditLogs(String userId) {
        return auditRepository.findAllByTargetId(userId);
    }

    @Override
    public String signUp(String name, String password) {
        AppLogger.info("UserService", "회원가입 시도: " + name);
        if (userRepository.findByName(name).isPresent()) {
            throw new IllegalStateException("이미 존재하는 사용자 이름입니다.");
        }

        User user = new User(name, password);
        userRepository.save(user);
        auditRepository.save(new AuditLog(user.getId(), "USER_SIGN_UP", "회원가입 완료"));
        return user.getId();
    }

    @Override
    public User login(String name, String password) {
        AppLogger.info("UserService", "로그인 시도: " + name);
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!user.getPassword().equals(password)) {
            AppLogger.info("UserService", "로그인 실패: 비밀번호 불일치 - " + name);
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        auditRepository.save(new AuditLog(user.getId(), "USER_LOGIN", "로그인 성공"));
        return user;
    }

    @Override
    public void withdraw(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        userRepository.delete(userId);
        auditRepository.save(new AuditLog(userId, "USER_WITHDRAW", "회원 탈퇴"));
    }

    @Override
    public Money getBalance(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return user.getBalance();
    }

    @Override
    public void deposit(String userId, Money amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        user.deposit(amount);
        userRepository.update(user);
        auditRepository.save(new AuditLog(user.getId(), "DEPOSIT", "입금: " + amount));
    }

    @Override
    public void withdrawMoney(String userId, Money amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        user.withdraw(amount);
        userRepository.update(user);
        auditRepository.save(new AuditLog(user.getId(), "WITHDRAW_MONEY", "출금: " + amount));
    }
}

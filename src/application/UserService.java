package application;

import domain.audit.AuditLog;
import domain.audit.AuditRepository;
import domain.common.valueobject.Money;
import domain.user.User;
import domain.user.UserRepository;
import infrastructure.logger.AppLogger;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

public class UserService {

    private final UserRepository userRepository;
    private final AuditRepository auditRepository;
    private String currentUserSessionId = null;

    public UserService(UserRepository userRepository, AuditRepository auditRepository) {
        this.userRepository = userRepository;
        this.auditRepository = auditRepository;
    }

    public List<AuditLog> getMyAuditLogs() {
        if (currentUserSessionId == null)
            return Collections.emptyList();
        return auditRepository.findAllByTargetId(currentUserSessionId);
    }

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

    public User login(String name, String password) {
        AppLogger.info("UserService", "로그인 시도: " + name);
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!user.getPassword().equals(password)) {
            AppLogger.info("UserService", "로그인 실패: 비밀번호 불일치 - " + name);
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        currentUserSessionId = user.getId();
        auditRepository.save(new AuditLog(user.getId(), "USER_LOGIN", "로그인 성공"));
        return user;
    }

    public void logout() {
        if (currentUserSessionId != null) {
            auditRepository.save(new AuditLog(currentUserSessionId, "USER_LOGOUT", "로그아웃"));
        }
        currentUserSessionId = null;
    }

    public void withdraw() {
        if (currentUserSessionId == null) {
            throw new RuntimeException("탈퇴할 계정이 존재하지 않습니다.");
        }

        User user = userRepository.findById(currentUserSessionId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        userRepository.delete(currentUserSessionId);
        auditRepository.save(new AuditLog(currentUserSessionId, "USER_WITHDRAW", "회원 탈퇴"));
        logout();
    }

    public Optional<String> getCurrentUserId() {
        return Optional.ofNullable(currentUserSessionId);
    }

    public Money getBalance() {
        if (currentUserSessionId == null)
            throw new RuntimeException("로그인이 필요합니다.");

        User user = userRepository.findById(currentUserSessionId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return user.getBalance();
    }

    public void deposit(Money amount) {
        if (currentUserSessionId == null)
            throw new RuntimeException("로그인이 필요합니다.");

        User user = userRepository.findById(currentUserSessionId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        user.deposit(amount);
        userRepository.update(user);
        auditRepository.save(new AuditLog(user.getId(), "DEPOSIT", "입금: " + amount));
    }

    public void withdrawMoney(Money amount) {
        if (currentUserSessionId == null)
            throw new RuntimeException("로그인이 필요합니다.");

        User user = userRepository.findById(currentUserSessionId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        user.withdraw(amount);
        userRepository.update(user);
        auditRepository.save(new AuditLog(user.getId(), "WITHDRAW_MONEY", "출금: " + amount));
    }
}

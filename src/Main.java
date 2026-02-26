import application.PaymentService;
import application.SubscriptionService;
import application.UserService;
import domain.audit.AuditLog;
import domain.common.valueobject.Money;
import domain.plan.Plan;
import domain.plan.PlanCatalog;
import domain.plan.PlanName;
import domain.user.User;
import infrastructure.config.AppConfig;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PlanCatalog planCatalog = new PlanCatalog();
    private static final UserService userService = AppConfig.getUserService();
    private static final SubscriptionService subscriptionService = AppConfig.getSubscriptionService();
    private static final PaymentService paymentService = AppConfig.getPaymentService();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Welcome to SaaS Subscription System   ");
        System.out.println("========================================");

        while (true) {
            if (userService.getCurrentUserId().isEmpty()) {
                showGuestMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private static void showGuestMenu() {
        System.out.println("\n[게스트 메뉴]");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("3. 플랜 구경하기");
        System.out.println("4. 종료");
        System.out.print("선택: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> signUp();
            case "2" -> login();
            case "3" -> listPlans();
            case "4" -> System.exit(0);
            default -> System.out.println("잘못된 선택입니다.");
        }
    }

    private static void showUserMenu() {
        String userId = userService.getCurrentUserId().get();
        Money balance = userService.getBalance();
        System.out.println("\n[회원 메뉴 - ID: " + userId + " | 잔액: " + balance + "]");
        System.out.println("1. 구독 시작하기");
        System.out.println("2. 결제 시뮬레이션 (잔액으로 차감)");
        System.out.println("3. 입금하기");
        System.out.println("4. 출금하기");
        System.out.println("5. 활동 로그 확인 (Audit)");
        System.out.println("6. 로그아웃");
        System.out.println("7. 회원 탈퇴");
        System.out.print("선택: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> subscribe(userId);
            case "2" -> simulatePayment(userId);
            case "3" -> depositFunds();
            case "4" -> withdrawFunds();
            case "5" -> showAuditLogs(userId);
            case "6" -> userService.logout();
            case "7" -> {
                userService.withdraw();
                System.out.println("탈퇴 처리되었습니다.");
            }
            default -> System.out.println("잘못된 선택입니다.");
        }
    }

    private static void signUp() {
        System.out.println("\n[회원가입]");
        System.out.print("사용자 이름: ");
        String name = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        try {
            userService.signUp(name, password);
            System.out.println("회원가입 성공!");
        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
        }
    }

    private static void login() {
        System.out.println("\n[로그인]");
        System.out.print("사용자 이름: ");
        String name = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        try {
            userService.login(name, password);
            System.out.println("로그인 성공!");
        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
        }
    }

    private static void listPlans() {
        System.out.println("\n[구독 플랜 목록]");
        planCatalog.getAllPlans().forEach(plan -> {
            System.out.println("- " + plan.getName() + ": " + plan.getPrice() + " (" + plan.getPeriod() + "일)");
        });
    }

    private static void subscribe(String userId) {
        System.out.println("\n[구독 시작]");
        listPlans();
        System.out.print("원하는 플랜 이름 입력 (FREE, PRO, BUSINESS): ");
        String planNameInput = scanner.nextLine().toUpperCase().trim();

        try {
            PlanName planName = PlanName.valueOf(planNameInput);
            Optional<Plan> plan = planCatalog.findPlanByName(planName);

            if (plan.isPresent()) {
                String subscriptionId = subscriptionService.startSubscription(userId, plan.get());
                System.out.println("구독이 시작되었습니다! (구독ID: " + subscriptionId + ")");
            } else {
                System.out.println("존재하지 않는 플랜입니다.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("잘못된 플랜 이름입니다.");
        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
        }
    }

    private static void depositFunds() {
        System.out.print("입금할 금액 입력: ");
        try {
            Long amount = Long.parseLong(scanner.nextLine());
            userService.deposit(new Money(amount));
            System.out.println("입금 완료!");
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력 가능합니다.");
        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
        }
    }

    private static void withdrawFunds() {
        System.out.print("출금할 금액 입력: ");
        try {
            Long amount = Long.parseLong(scanner.nextLine());
            userService.withdrawMoney(new Money(amount));
            System.out.println("출금 완료!");
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력 가능합니다.");
        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
        }
    }

    private static void showAuditLogs(String userId) {
        System.out.println("\n--- 활동 로그 (Audit Log) ---");
        List<AuditLog> logs = userService.getMyAuditLogs();
        if (logs.isEmpty()) {
            System.out.println("기록된 활동 로그가 없습니다.");
        } else {
            logs.forEach(log -> System.out.println(log.toString()));
        }
    }

    private static void simulatePayment(String userId) {
        System.out.println("\n--- 결제 시뮬레이션 ---");
        System.out.println("잔액을 사용하여 구독 결제를 진행합니다.");
        System.out.print("구독 ID를 입력하세요: ");
        String subId = scanner.nextLine();

        try {
            paymentService.processPayment(subId);
            System.out.println("결제 처리 프로세스 완료. (잔액 부족 시 PAST_DUE로 변경됨)");
        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
        }
    }
}
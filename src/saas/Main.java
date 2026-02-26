package saas;

import saas.domain.Payment;
import saas.domain.Plan;
import saas.domain.PlanType;
import saas.domain.Subscription;
import saas.domain.User;
import saas.repository.PaymentRepository;
import saas.repository.PlanRepository;
import saas.repository.SubscriptionRepository;
import saas.repository.UserRepository;
import saas.repository.memory.InMemoryPaymentRepository;
import saas.repository.memory.InMemoryPlanRepository;
import saas.repository.memory.InMemorySubscriptionRepository;
import saas.repository.memory.InMemoryUserRepository;
import saas.service.SubscriptionAppService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new InMemoryUserRepository();
        PlanRepository planRepository = new InMemoryPlanRepository();
        SubscriptionRepository subscriptionRepository = new InMemorySubscriptionRepository();
        PaymentRepository paymentRepository = new InMemoryPaymentRepository();

        seedPlans(planRepository);

        SubscriptionAppService service = new SubscriptionAppService(
                userRepository,
                planRepository,
                subscriptionRepository,
                paymentRepository
        );

        runMenu(service);
    }

    private static void runMenu(SubscriptionAppService service) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("번호 입력: ");
            String input = scanner.nextLine().trim();

            try {
                int menu = Integer.parseInt(input);

                if (menu == 0) {
                    System.out.println("종료합니다.");
                    return;
                }

                handleMenu(menu, service, scanner);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            } catch (Exception e) {
                System.out.println("실패: " + e.getMessage());
            }
        }
    }

    private static void handleMenu(int menu, SubscriptionAppService service, Scanner scanner) {
        switch (menu) {
            case 1 -> {
                System.out.print("email: ");
                User user = service.registerUser(scanner.nextLine().trim());
                System.out.println("등록 완료 userId=" + user.getUserId());
            }
            case 2 -> printUsers(service.getUsers());
            case 3 -> printPlans(service.getPlans());
            case 4 -> {
                System.out.print("userId: ");
                String userId = scanner.nextLine().trim();
                System.out.print("plan (FREE/PRO/BUSINESS): ");
                PlanType planType = PlanType.valueOf(scanner.nextLine().trim().toUpperCase());
                Subscription s = service.subscribe(userId, planType);
                printSubscription(s);
            }
            case 5 -> {
                System.out.print("userId: ");
                printSubscriptions(service.getSubscriptionsByUser(scanner.nextLine().trim()));
            }
            case 6 -> {
                System.out.print("subscriptionId: ");
                printPayment(service.charge(scanner.nextLine().trim(), true));
            }
            case 7 -> {
                System.out.print("subscriptionId: ");
                printPayment(service.charge(scanner.nextLine().trim(), false));
            }
            case 8 -> {
                System.out.print("subscriptionId: ");
                String subscriptionId = scanner.nextLine().trim();
                System.out.print("refund amount: ");
                long amount = Long.parseLong(scanner.nextLine().trim());
                printPayment(service.refund(subscriptionId, amount));
            }
            case 9 -> {
                System.out.print("subscriptionId: ");
                String id = scanner.nextLine().trim();
                service.pause(id);
                printSubscription(service.getSubscription(id));
            }
            case 10 -> {
                System.out.print("subscriptionId: ");
                String id = scanner.nextLine().trim();
                service.resume(id);
                printSubscription(service.getSubscription(id));
            }
            case 11 -> {
                System.out.print("subscriptionId: ");
                String id = scanner.nextLine().trim();
                service.cancel(id);
                printSubscription(service.getSubscription(id));
            }
            case 12 -> {
                System.out.print("subscriptionId(전체면 엔터): ");
                String subscriptionId = scanner.nextLine().trim();
                if (subscriptionId.isEmpty()) {
                    printPayments(service.getPayments());
                } else {
                    printPayments(service.getPaymentsBySubscription(subscriptionId));
                }
            }
            default -> System.out.println("없는 메뉴입니다.");
        }
    }

    private static void printMenu() {
        System.out.println("\n=== SaaS 메뉴 ===");
        System.out.println("0. 종료");
        System.out.println("1. 유저 등록");
        System.out.println("2. 유저 목록");
        System.out.println("3. 플랜 목록");
        System.out.println("4. 구독 생성");
        System.out.println("5. 유저 구독 조회");
        System.out.println("6. 결제 성공 처리");
        System.out.println("7. 결제 실패 처리");
        System.out.println("8. 환불");
        System.out.println("9. 구독 일시정지");
        System.out.println("10. 구독 재개");
        System.out.println("11. 구독 해지");
        System.out.println("12. 결제 내역 조회");
    }

    private static void printUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("유저가 없습니다.");
            return;
        }
        users.forEach(u -> System.out.printf("userId=%s, email=%s, status=%s%n", u.getUserId(), u.getEmail(), u.getStatus()));
    }

    private static void printPlans(List<Plan> plans) {
        plans.forEach(plan -> System.out.printf("plan=%s, price=%d, periodDays=%d, limits=%s%n",
                plan.getPlanType(), plan.getPrice(), plan.getPeriodDays(), plan.getLimits()));
    }

    private static void printSubscriptions(List<Subscription> subscriptions) {
        if (subscriptions.isEmpty()) {
            System.out.println("구독이 없습니다.");
            return;
        }
        subscriptions.forEach(Main::printSubscription);
    }

    private static void printSubscription(Subscription s) {
        System.out.printf(
                "subscriptionId=%s, userId=%s, planType=%s, status=%s, priceSnapshot=%d, periodDaysSnapshot=%d%n",
                s.getSubscriptionId(), s.getUserId(), s.getPlanType(), s.getStatus(), s.getPriceSnapshot(), s.getPeriodDaysSnapshot()
        );
    }

    private static void printPayment(Payment p) {
        System.out.printf("paymentId=%s, subscriptionId=%s, type=%s, amount=%d, status=%s, occurredAt=%s%n",
                p.getPaymentId(), p.getSubscriptionId(), p.getType(), p.getAmount(), p.getStatus(), p.getOccurredAt());
    }

    private static void printPayments(List<Payment> payments) {
        if (payments.isEmpty()) {
            System.out.println("결제 내역이 없습니다.");
            return;
        }
        payments.forEach(Main::printPayment);
    }

    private static void seedPlans(PlanRepository planRepository) {
        planRepository.save(new Plan(PlanType.FREE, 0L, 30, Map.of("projects", 1, "storageGb", 1)));
        planRepository.save(new Plan(PlanType.PRO, 15000L, 30, Map.of("projects", 10, "storageGb", 50)));
        planRepository.save(new Plan(PlanType.BUSINESS, 49000L, 30, Map.of("projects", 100, "storageGb", 500)));
    }
}

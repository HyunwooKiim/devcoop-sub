package infrastructure;

import application.SubscriptionService;
import application.SubscriptionUseCase;
import domain.payment.Payment;
import domain.plan.Plan;
import domain.user.Subscription;
import domain.user.User;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static SubscriptionUseCase useCase;
    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser;

    public static void main(String[] args) {
        MemoryRepository repository = new MemoryRepository();
        repository.initData();
        useCase = new SubscriptionService(repository);
        currentUser = repository.getInitialUser();

        while (true) {
            showMenu();
            String input = scanner.nextLine();

            if (input.equals("1")) listPlans();
            else if (input.equals("2")) myInfo();
            else if (input.equals("3")) subscribe();
            else if (input.equals("4")) simulatePayment();
            else if (input.equals("5")) cancelSubscription();
            else if (input.equals("0")) {
                System.out.println("종료합니다.");
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n==========");
        System.out.println("1. 플랜 목록 보기");
        System.out.println("2. 내 구독 정보");
        System.out.println("3. 구독 신청하기");
        System.out.println("4. 결제 시뮬레이션");
        System.out.println("5. 구독 해지");
        System.out.println("0. 종료");
        System.out.print("선택: ");
    }

    private static void listPlans() {
        System.out.println("\n[플랜 목록]");
        for (Plan plan : useCase.getPlans()) {
            System.out.println("ID: " + plan.getId() + " | 타입: " + plan.getType() + " | 가격: " + plan.getPrice() + "원");
        }
    }

    private static void myInfo() {
        User user = useCase.getSubscriptionInfo(currentUser.getId());
        System.out.println("\n[내 구독 내역] User ID: " + user.getId());
        if (user.getSubscriptions().isEmpty()) {
            System.out.println("구독 내역이 없습니다.");
            return;
        }
        for (Subscription sub : user.getSubscriptions()) {
            System.out.println("- 구독ID: " + sub.getId() + " | 플랜ID: " + sub.getPlanId() +
                    " | 결제금액: " + sub.getPrice() + " | 상태: " + sub.getStatus());
        }
    }

    private static void subscribe() {
        try {
            listPlans();
            System.out.print("가입할 플랜 ID 입력: ");
            Long planId = Long.parseLong(scanner.nextLine());

            Payment payment = useCase.requestSubscription(currentUser.getId(), planId);
            System.out.println("구독 신청이 완료되었습니다. 결제를 진행해주세요. (결제 대기번호: " + payment.getId() + ")");
        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
        }
    }

    private static void simulatePayment() {
        System.out.println("\n[결제 대기 목록]");
        List<Payment> pendingPayments = useCase.getPendingPayments();
        if (pendingPayments.isEmpty()) {
            System.out.println("결제 대기중인 항목이 없습니다.");
            return;
        }
        pendingPayments.forEach(p ->
                System.out.println("결제ID: " + p.getId() + " | 상태: " + p.getStatus() + " | 금액: " + p.getPrice())
        );

        System.out.print("처리할 결제 ID 입력: ");
        Long paymentId = Long.parseLong(scanner.nextLine());
        System.out.print("1: 결제 승인, 2: 결제 실패 -> ");
        String choice = scanner.nextLine();

        try {
            if (choice.equals("1")) {
                useCase.processPaymentSuccess(paymentId);
                System.out.println("결제가 성공적으로 처리되었습니다.");
            } else if (choice.equals("2")) {
                useCase.processPaymentFailure(paymentId);
                System.out.println("결제가 실패하여 구독이 PAST_DUE(연체) 상태로 변경되었습니다.");
            }
        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
        }
    }

    private static void cancelSubscription() {
        System.out.println("\n[구독 해지]");
        myInfo();
        User user = useCase.getSubscriptionInfo(currentUser.getId());
        if (user.getSubscriptions().stream().noneMatch(s -> s.getStatus() != domain.user.SubscriptionStatus.CANCELED)) {
            return;
        }

        System.out.print("\n해지할 구독 ID를 입력하세요 (돌아가려면 0): ");
        Long subscriptionId = Long.parseLong(scanner.nextLine());

        if (subscriptionId == 0L) {
            System.out.println("취소했습니다.");
            return;
        }

        try {
            useCase.cancelSubscription(currentUser.getId(), subscriptionId);
            System.out.println("구독이 성공적으로 해지(CANCELED) 되었습니다.");
        } catch (Exception e) {
            System.out.println("해지 실패: " + e.getMessage());
        }
    }
}
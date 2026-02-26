package controller;

import domain.payment.Payment;
import domain.plan.Plan;
import domain.user.Subscription;
import domain.user.User;
import repository.MemoryRepository;
import service.SubscriptionService;

import java.util.Scanner;

public class Controller {
    private final SubscriptionService service = new SubscriptionService();
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        System.out.println("\n==========");
        System.out.println("1. 플랜 목록 보기");
        System.out.println("2. 유저 내 정보 (구독 내역)");
        System.out.println("3. 구독 신청하기");
        System.out.println("4. 결제 승인/실패 시뮬레이션");
        System.out.println("5. 구독 해지");
        System.out.println("0. 종료");
        System.out.print("선택: ");
    }

    public void listPlans() {
        System.out.println("\n[플랜 목록]");
        for (Plan plan : MemoryRepository.plans.values()) {
            System.out.println("ID: " + plan.getId() + " | 타입: " + plan.getType() + " | 가격: " + plan.getPrice() + "원");
        }
    }

    public void myInfo(Long userId) {
        User user = MemoryRepository.users.get(userId);
        System.out.println("\n[내 구독 내역] User ID: " + userId);
        if (user.getSubscriptions().isEmpty()) {
            System.out.println("구독 내역이 없습니다.");
            return;
        }
        for (Subscription sub : user.getSubscriptions()) {
            System.out.println("- 구독ID: " + sub.getId() + " | 플랜ID: " + sub.getPlanId() +
                    " | 결제금액: " + sub.getPrice() + " | 상태: " + sub.getStatus());
        }
    }

    public void subscribe(Long userId) {
        try {
            listPlans();
            System.out.print("가입할 플랜 ID 입력: ");
            Long planId = Long.parseLong(scanner.nextLine());

            Payment payment = service.requestSubscription(userId, planId);
            System.out.println("구독 신청이 완료되었습니다. 결제를 진행해주세요. (결제 대기번호: " + payment.getId() + ")");
        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
        }
    }

    public void simulatePayment() {
        System.out.println("\n[결제 대기 목록]");
        MemoryRepository.payments.values().forEach(p ->
                System.out.println("결제ID: " + p.getId() + " | 상태: " + p.getStatus() + " | 금액: " + p.getPrice())
        );

        System.out.print("처리할 결제 ID 입력: ");
        Long paymentId = Long.parseLong(scanner.nextLine());
        System.out.print("1: 결제 승인, 2: 결제 실패 (잔액 부족 등) -> ");
        String choice = scanner.nextLine();

        try {
            if (choice.equals("1")) {
                service.paymentSuccess(paymentId);
                System.out.println("결제가 성공적으로 처리되었습니다.");
            } else if (choice.equals("2")) {
                service.paymentFail(paymentId);
                System.out.println("결제가 실패하여 구독이 PAST_DUE(연체) 상태로 변경되었습니다.");
            }
        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
        }
    }

    public void cancelSubscription(Long userId) {
        System.out.println("\n[구독 해지]");
        myInfo(userId);

        System.out.print("\n해지할 구독 ID를 입력하세요 (돌아가려면 0): ");
        Long subscriptionId = Long.parseLong(scanner.nextLine());

        if (subscriptionId == 0L) {
            System.out.println("취소했습니다.");
            return;
        }

        try {
            service.cancelSubscription(userId, subscriptionId);
            System.out.println("구독이 성공적으로 해지(CANCELED) 되었습니다.");
        } catch (Exception e) {
            System.out.println("해지 실패: " + e.getMessage());
        }
    }
}
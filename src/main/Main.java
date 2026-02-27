package main;

import main.application.subscription.dto.CreateSubscriptionRequest;
import main.application.subscription.dto.ProcessPaymentRequest;
import main.application.subscription.dto.SubscriptionResponse;
import main.application.subscription.dto.PaymentResponse;
import main.application.subscription.external.PlanRepository;
import main.application.subscription.external.SubscriptionRepository;
import main.application.subscription.external.UserRepository;
import main.application.subscription.interactor.CreateSubscriptionInteractor;
import main.application.subscription.interactor.ProcessPaymentInteractor;
import main.application.subscription.internal.CreateSubscriptionUseCase;
import main.application.subscription.internal.ProcessPaymentUseCase;
import main.domain.plan.internal.Plan;
import main.domain.plan.internal.PlanRoot;
import main.domain.plan.vo.PlanType;
import main.domain.subscription.internal.Subscription;
import main.domain.subscription.internal.SubscriptionRoot;
import main.domain.user.internal.User;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SaaS 구독 시스템 시뮬레이션 ===\n");

        // 저장소 초기화 (In-Memory)
        InMemoryPlanRepository planRepository = new InMemoryPlanRepository();
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemorySubscriptionRepository subscriptionRepository = new InMemorySubscriptionRepository();

        // 플랜 설정
        Plan freePlan = new PlanRoot(1L, PlanType.FREE, 0L, 30L);     // 무료, 30일
        Plan proPlan = new PlanRoot(2L, PlanType.PRO, 10000L, 30L);   // 10,000원, 30일
        Plan businessPlan = new PlanRoot(3L, PlanType.BUSINESS, 30000L, 30L); // 30,000원, 30일

        planRepository.save(freePlan);
        planRepository.save(proPlan);
        planRepository.save(businessPlan);

        // 사용자 설정
        userRepository.createUser(1L, "Alice");
        userRepository.createUser(2L, "Bob");

        // Use Cases 주입
        CreateSubscriptionUseCase createSubscriptionUseCase = new CreateSubscriptionInteractor(
            subscriptionRepository, planRepository, userRepository
        );
        ProcessPaymentUseCase processPaymentUseCase = new ProcessPaymentInteractor(subscriptionRepository);

        // ===== 시나리오 1: Alice가 Pro 플랜으로 구독 =====
        System.out.println("[시나리오 1] Alice가 Pro 플랜으로 구독 신청");
        CreateSubscriptionRequest req1 = new CreateSubscriptionRequest(1L, 2L);
        SubscriptionResponse resp1 = createSubscriptionUseCase.execute(req1);
        
        System.out.println("구독 ID: " + resp1.getSubscriptionId());
        System.out.println("플랜 ID: " + resp1.getPlanId());
        System.out.println("금액: " + resp1.getCharge() + "원");
        System.out.println("상태: " + resp1.getStatus() + " (결제 대기 중)");
        System.out.println("시작일: " + resp1.getStartDate());
        System.out.println("종료일: " + resp1.getEndDate());
        long subscriptionId1 = resp1.getSubscriptionId();
        System.out.println();

        // ===== 시나리오 2: 결제 실패 처리 =====
        System.out.println("[시나리오 2] Alice의 결제 실패 처리");
        ProcessPaymentRequest payReq1 = new ProcessPaymentRequest(subscriptionId1, false);
        PaymentResponse payResp1 = processPaymentUseCase.execute(payReq1);
        
        System.out.println("구독 ID: " + payResp1.getSubscriptionId());
        System.out.println("결제 상태: " + payResp1.getPaymentStatus());
        System.out.println("구독 상태: " + payResp1.getSubscriptionStatus() + " (결제 실패로 변경)");
        System.out.println("금액: " + payResp1.getCharge() + "원");
        System.out.println();

        // ===== 시나리오 3: Alice의 결제 재시도 =====
        System.out.println("[시나리오 3] Alice의 결제 재시도 (성공)");
        ProcessPaymentRequest payReq2 = new ProcessPaymentRequest(subscriptionId1, true);
        PaymentResponse payResp2 = processPaymentUseCase.execute(payReq2);
        
        System.out.println("구독 ID: " + payResp2.getSubscriptionId());
        System.out.println("결제 상태: " + payResp2.getPaymentStatus() + " (결제 완료)");
        System.out.println("구독 상태: " + payResp2.getSubscriptionStatus() + " (활성화됨)");
        System.out.println("금액: " + payResp2.getCharge() + "원");
        System.out.println();

        // ===== 시나리오 4: Bob이 무료 플랜으로 구독 =====
        System.out.println("[시나리오 4] Bob이 Free 플랜으로 구독 신청");
        CreateSubscriptionRequest req2 = new CreateSubscriptionRequest(2L, 1L);
        SubscriptionResponse resp2 = createSubscriptionUseCase.execute(req2);
        
        System.out.println("구독 ID: " + resp2.getSubscriptionId());
        System.out.println("플랜: Free");
        System.out.println("금액: " + resp2.getCharge() + "원 (무료)");
        System.out.println("상태: " + resp2.getStatus() + " (무료이므로 자동 활성화)");
        long subscriptionId2 = resp2.getSubscriptionId();
        System.out.println();

        // ===== 시나리오 5: Bob의 구독을 Business 플랜으로 업그레이드 =====
        System.out.println("[시나리오 5] Bob의 구독을 Business 플랜으로 업그레이드");
        Subscription sub2 = subscriptionRepository.findById(subscriptionId2);
        System.out.println("기존 플랜: " + sub2.getPlan().getType());
        System.out.println("변경 전 상태: " + sub2.getStatus());
        System.out.println("변경 전 금액: " + sub2.getCharge() + "원");
        
        sub2.changePlan(businessPlan);
        subscriptionRepository.update(sub2);
        
        System.out.println("\n변경 후 플랜: " + sub2.getPlan().getType());
        System.out.println("변경 후 상태: " + sub2.getStatus() + " (결제 대기)");
        System.out.println("변경 후 금액: " + sub2.getCharge() + "원");
        System.out.println();

        // ===== 시나리오 6: Bob의 업그레이드 결제 처리 =====
        System.out.println("[시나리오 6] Bob의 Business 플랜 결제 완료");
        ProcessPaymentRequest payReq3 = new ProcessPaymentRequest(subscriptionId2, true);
        PaymentResponse payResp3 = processPaymentUseCase.execute(payReq3);
        
        System.out.println("구독 ID: " + payResp3.getSubscriptionId());
        System.out.println("결제 상태: " + payResp3.getPaymentStatus());
        System.out.println("구독 상태: " + payResp3.getSubscriptionStatus());
        System.out.println("금액: " + payResp3.getCharge() + "원");
        System.out.println();

        // ===== 최종 상태 확인 =====
        System.out.println("[최종 상태]");
        Subscription finalSub1 = subscriptionRepository.findById(subscriptionId1);
        Subscription finalSub2 = subscriptionRepository.findById(subscriptionId2);
        
        System.out.println("Alice의 구독:");
        System.out.println("  - 플랜: " + finalSub1.getPlan().getType());
        System.out.println("  - 상태: " + finalSub1.getStatus());
        System.out.println("  - 활성 여부: " + finalSub1.isActivated());
        System.out.println("  - 결제 상태: " + finalSub1.getPayment().getStatus());
        
        System.out.println("\nBob의 구독:");
        System.out.println("  - 플랜: " + finalSub2.getPlan().getType());
        System.out.println("  - 상태: " + finalSub2.getStatus());
        System.out.println("  - 활성 여부: " + finalSub2.isActivated());
        System.out.println("  - 결제 상태: " + finalSub2.getPayment().getStatus());
    }

    // ===== 메모리 기반 저장소 구현 =====
    
    static class InMemoryPlanRepository implements PlanRepository {
        private Map<Long, Plan> plans = new HashMap<>();

        void save(Plan plan) {
            // PlanRoot를 캐스팅해서 ID를 얻음
            if (plan instanceof PlanRoot) {
                long id = ((PlanRoot) plan).getId();
                plans.put(id, plan);
            }
        }

        long getPlanId(Plan plan) {
            if (plan instanceof PlanRoot) {
                return ((PlanRoot) plan).getId();
            }
            return -1L;
        }

        @Override
        public Plan findById(Long planId) {
            return plans.get(planId);
        }

        @Override
        public Plan findByType(String planType) {
            return plans.values().stream()
                .filter(p -> p.getType().equals(planType))
                .findFirst()
                .orElse(null);
        }
    }

    static class InMemoryUserRepository implements UserRepository {
        private Map<Long, User> users = new HashMap<>();

        void createUser(Long userId, String name) {
            users.put(userId, new SimpleUser(userId, name));
        }

        @Override
        public User findById(Long userId) {
            return users.get(userId);
        }

        @Override
        public void update(User user) {
            users.put(user.getId(), user);
        }

        static class SimpleUser implements User {
            private Long id;
            private String name;
            private Subscription subscription;

            SimpleUser(Long id, String name) {
                this.id = id;
                this.name = name;
            }

            @Override
            public Long getId() {
                return id;
            }

            @Override
            public Subscription getSubscription() {
                return subscription;
            }

            public void setSubscription(Subscription subscription) {
                this.subscription = subscription;
            }

            public String getName() {
                return name;
            }
        }
    }

    static class InMemorySubscriptionRepository implements SubscriptionRepository {
        private Map<Long, Subscription> subscriptions = new HashMap<>();
        private long nextId = 1L;

        @Override
        public void save(Subscription subscription) {
            Long id = nextId++;
            setSubscriptionId(subscription, id);
            subscriptions.put(id, subscription);
        }

        @Override
        public Subscription findById(Long id) {
            return subscriptions.get(id);
        }

        @Override
        public void update(Subscription subscription) {
            subscriptions.put(subscription.getId(), subscription);
        }

        Long getLastInsertedId() {
            return nextId - 1;
        }

        private void setSubscriptionId(Subscription subscription, Long id) {
            try {
                Field idField = SubscriptionRoot.class.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(subscription, id);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}

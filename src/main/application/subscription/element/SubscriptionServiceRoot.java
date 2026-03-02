package main.application.subscription.element;

import java.util.List;
import java.util.stream.Collectors;

import main.application.subscription.dto.ChargeDto;
import main.application.subscription.dto.PlanInfoDto;
import main.application.subscription.dto.SubscriptionStatusDto;
import main.application.subscription.external.PaymentRepository;
import main.application.subscription.external.PlanRepository;
import main.application.subscription.external.SubscriptionRepository;
import main.application.subscription.internal.SubscriptionService;
import main.domain.payment.Payment;
import main.domain.plan.Plan;
import main.domain.subscription.Subscription;

public class SubscriptionServiceRoot implements SubscriptionService {
    
    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final PaymentRepository paymentRepository;
    
    public SubscriptionServiceRoot(
            SubscriptionRepository subscriptionRepository,
            PlanRepository planRepository,
            PaymentRepository paymentRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public SubscriptionStatusDto getSubscriptionStatus(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        
        if (subscription == null || subscription.getPlan() == null) {
            return null;
        }
        
        Plan plan = subscription.getPlan();
        return new SubscriptionStatusDto(
                plan.getPlanType(),
                subscription.isActive(),
                subscription.getEndDate(),
                plan.getCharge(),
                plan.getPeriod()
        );
    }

    @Override
    public SubscriptionStatusDto changePlan(Long userId, Long newPlanId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        
        if (subscription == null) {
            throw new RuntimeException("Subscription not found for user: " + userId);
        }
        
        Plan newPlan = planRepository.findById(newPlanId);
        if (newPlan == null) {
            throw new RuntimeException("Plan not found: " + newPlanId);
        }
        
        subscription.changePlan(newPlan);
        subscriptionRepository.update(subscription);
        
        return new SubscriptionStatusDto(
                newPlan.getPlanType(),
                subscription.isActive(),
                subscription.getEndDate(),
                newPlan.getCharge(),
                newPlan.getPeriod()
        );
    }

    @Override
    public ChargeDto getCharge(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        
        if (subscription == null || subscription.getPlan() == null) {
            return new ChargeDto(0L, false, "No subscription found");
        }
        
        // 구독이 활성화되어 있으면 추가 결제 불필요
        if (subscription.isActive()) {
            return new ChargeDto(0L, false, "Subscription is active");
        }
        
        // 비활성화 상태면 현재 플랜의 가격만큼 결제 필요
        Plan currentPlan = subscription.getPlan();
        return new ChargeDto(currentPlan.getCharge(), true, "Payment required to activate subscription");
    }

    @Override
    public List<PlanInfoDto> getPlanList() {
        List<Plan> plans = planRepository.findAll();
        
        return plans.stream()
                .map(plan -> new PlanInfoDto(
                        plan.getId(),
                        plan.getPlanType(),
                        plan.getCharge(),
                        plan.getPeriod()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionStatusDto payCharge(Long userId, Long paymentId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        
        if (subscription == null) {
            throw new RuntimeException("Subscription not found for user: " + userId);
        }
        
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new RuntimeException("Payment not found: " + paymentId);
        }
        
        // 결제 정보를 구독에 반영
        subscription.updatePayment(payment);
        
        // 결제가 성공했으면 구독 활성화
        if (payment.isPaid() && payment.getCharge().equals(subscription.getPlan().getCharge())) {
            subscription.activateSubscription();
        }
        
        subscriptionRepository.update(subscription);
        
        Plan plan = subscription.getPlan();
        return new SubscriptionStatusDto(
                plan.getPlanType(),
                subscription.isActive(),
                subscription.getEndDate(),
                plan.getCharge(),
                plan.getPeriod()
        );
    }
}

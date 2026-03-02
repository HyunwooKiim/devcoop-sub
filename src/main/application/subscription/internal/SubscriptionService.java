package main.application.subscription.internal;

import java.util.List;

import main.application.subscription.dto.ChargeDto;
import main.application.subscription.dto.PlanInfoDto;
import main.application.subscription.dto.SubscriptionStatusDto;

public interface SubscriptionService {
    /**
     * 구독 상태 조회 - 현재 구독 중인 플랜과 활성화 상태
     */
    SubscriptionStatusDto getSubscriptionStatus(Long userId);

    /**
     * 플랜 변경
     */
    SubscriptionStatusDto changePlan(Long userId, Long newPlanId);

    /**
     * 현재 상태에서 지불해야 할 금액 조회
     * (활성화 상태일 때는 0)
     */
    ChargeDto getCharge(Long userId);

    /**
     * 구독 가능한 플랜 목록 조회 - 가격과 기간 포함
     */
    List<PlanInfoDto> getPlanList();

    /**
     * 결제 완료 - 결제 이후 구독 상태에 반영
     */
    SubscriptionStatusDto payCharge(Long userId, Long paymentId);
}

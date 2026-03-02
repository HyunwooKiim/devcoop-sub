package main.adapter.subscription;

import java.util.List;

import main.adapter.common.ApiResponse;
import main.application.subscription.dto.ChargeDto;
import main.application.subscription.dto.PlanInfoDto;
import main.application.subscription.dto.SubscriptionStatusDto;
import main.application.subscription.internal.SubscriptionService;

public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * GET /api/subscriptions/{userId}/status
     * 구독 상태 조회
     */
    public ApiResponse<SubscriptionStatusDto> getSubscriptionStatus(Long userId) {
        try {
            if (userId == null || userId <= 0) {
                return ApiResponse.badRequest("Invalid user ID");
            }

            SubscriptionStatusDto status = subscriptionService.getSubscriptionStatus(userId);
            
            if (status == null) {
                return ApiResponse.notFound("No subscription found for user");
            }

            return ApiResponse.success("Subscription status retrieved", status);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * PUT /api/subscriptions/{userId}/plan/{newPlanId}
     * 플랜 변경
     */
    public ApiResponse<SubscriptionStatusDto> changePlan(Long userId, Long newPlanId) {
        try {
            if (userId == null || userId <= 0) {
                return ApiResponse.badRequest("Invalid user ID");
            }

            if (newPlanId == null || newPlanId <= 0) {
                return ApiResponse.badRequest("Invalid plan ID");
            }

            SubscriptionStatusDto status = subscriptionService.changePlan(userId, newPlanId);
            return ApiResponse.success("Plan changed successfully", status);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * GET /api/subscriptions/{userId}/charge
     * 결제 금액 조회
     */
    public ApiResponse<ChargeDto> getCharge(Long userId) {
        try {
            if (userId == null || userId <= 0) {
                return ApiResponse.badRequest("Invalid user ID");
            }

            ChargeDto chargeInfo = subscriptionService.getCharge(userId);
            return ApiResponse.success("Charge information retrieved", chargeInfo);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * GET /api/subscriptions/plans
     * 구독 가능한 플랜 목록 조회
     */
    public ApiResponse<List<PlanInfoDto>> getPlanList() {
        try {
            List<PlanInfoDto> plans = subscriptionService.getPlanList();
            return ApiResponse.success("Plan list retrieved", plans);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * POST /api/subscriptions/{userId}/payment/{paymentId}
     * 결제 완료 처리
     */
    public ApiResponse<SubscriptionStatusDto> payCharge(Long userId, Long paymentId) {
        try {
            if (userId == null || userId <= 0) {
                return ApiResponse.badRequest("Invalid user ID");
            }

            if (paymentId == null || paymentId <= 0) {
                return ApiResponse.badRequest("Invalid payment ID");
            }

            SubscriptionStatusDto status = subscriptionService.payCharge(userId, paymentId);
            return ApiResponse.success("Payment processed successfully", status);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}

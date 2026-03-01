package core.domain.subscription;

public enum SubscriptionStatus {
    CANCELED, // 해지
    PAUSED, // 일시정지
    PAST_DUE, // 연체
    ACTIVE, // 활성화
}

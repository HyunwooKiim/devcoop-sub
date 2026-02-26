# SaaS 구독/결제 과제 (Plain Java)

다이어그램 기준으로 아래 4개 애그리거트를 사용했습니다.

- `User (Root)`: `userId`, `email`, `status`, `register()`
- `Subscription (Root)`: `subscriptionId`, `userId`, `planType`, `status`, `startedAt`, `lastPaidAt`, `priceSnapshot`, `periodDaysSnapshot`
- `Payment (Root)`: `paymentId`, `subscriptionId`, `type`, `amount`, `status`, `occurredAt`
- `Plan (Root)`: `planType`, `currentPrice`, `billingPeriodDays`, `limits`

## Repository 구조

인터페이스 + 구현체 분리 형태입니다.

- Interface: `saas.repository.*`
- In-memory 구현: `saas.repository.memory.*`

예)
- `UserRepository` <- `InMemoryUserRepository`
- `SubscriptionRepository` <- `InMemorySubscriptionRepository`

## 반영한 규칙

- 유저는 동시에 하나의 유료 플랜만 `ACTIVE`
- 결제 실패 시 구독 상태 `PAST_DUE`
- 구독 시점의 가격/기간은 스냅샷으로 저장

## 실행

```bash
javac $(find src -name '*.java')
java -cp src saas.Main
```

## 터미널 숫자 메뉴

- `0` 종료
- `1` 유저 등록
- `2` 유저 목록
- `3` 플랜 목록
- `4` 구독 생성
- `5` 유저 구독 조회
- `6` 결제 성공 처리
- `7` 결제 실패 처리
- `8` 환불
- `9` 구독 일시정지
- `10` 구독 재개
- `11` 구독 해지
- `12` 결제 내역 조회

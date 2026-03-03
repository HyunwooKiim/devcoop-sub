# SaaS 구독/결제 과제 (Plain Java)

요청하신 4계층 구조로 분리했습니다.

## 1. Presentation Layer
- 위치: `src/saas/presentation`
- 역할: 사용자 입력/출력 (콘솔 메뉴)
- 파일: `Main.java`

## 2. Business Layer
- 위치: `src/saas/business`
- 역할: 도메인 모델 + 비즈니스 로직
- 구성:
  - `model`: `User`, `Subscription`, `Payment`, `Plan` 등
  - `repository`: Repository interface
  - `service`: `SubscriptionAppService`

## 3. Persistence Layer
- 위치: `src/saas/persistence`
- 역할: Repository interface 구현체
- 구성: `InMemoryUserRepository`, `InMemoryPlanRepository`, `InMemorySubscriptionRepository`, `InMemoryPaymentRepository`

## 4. Database Layer
- 위치: `src/saas/database`
- 역할: 실제 저장소 객체 제공
- 파일: `InMemoryDatabase.java`

Persistence Layer의 Repository 구현체는 `InMemoryDatabase`를 주입받아 데이터를 저장/조회합니다.

## 실행

```bash
javac $(find src -name '*.java')
java -classpath ./src saas.presentation.Main
```

## 숫자 메뉴
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

# 애그리거트
<img width="903" height="584" alt="image" src="https://github.com/user-attachments/assets/c3cb618d-30f0-4178-921f-f7417c0d347d" />



**애그리거트** : 도메인 영역을 구성하는 요소 중 하나로 관련된 도메인의 집합을 뜻함.

어떠한 도메인 모델을 이해하기 위해, 상위 수준 도메인 모델과 하위 수준 도메인 모델을 이해해보면

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fb5hRN9%2FbtrMmg33osK%2FAAAAAAAAAAAAAAAAAAAAAFarOTR1L3MfHhgoJAPKmCQVy_yrbT1FfLoM1nPwaXQK%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1772290799%26allow_ip%3D%26allow_referer%3D%26signature%3Dw5LA30Hpj6e6z1WU733rutcw5UA%253D)
상위 수준 도메인의 모델 경우 비교적 큰 개념 간의 관계를 나타낸다.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FehYDsO%2FbtrMlWqXtzU%2FAAAAAAAAAAAAAAAAAAAAAJxJElZx91XvNygyWlNxjqZRJnODVw1fLwNb81k7nBKC%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1772290799%26allow_ip%3D%26allow_referer%3D%26signature%3Dn1nPzywZcJFAUZuaVGWix3DWBtc%253D)
반면 하위 수준 도메인 모델의 경우 주로 객체 단위의 관계를 표현한다.

문제는 상위 수준 도메인 모델을 기반으로 하위 수준 도메인 모델을 도출해 냈을때,

객체간의 관계에 초점이 맞춰져 있어 하위 수준의 도메인을 보고서는 도메인의 전반적인 흐름이나

그 속에서의 주요 관계를 파악하기 어렵다.

이러한 문제를 해결하기 위해, 하위 수준 도메인 모델에서도 상위 수준 모델처럼

큰 개념 단위로 관계를 파악할 수 있도록 도입된 개념이 **애그리거트**이다.

또한 애그리거트는 강한 일관성을 유지해야 하는 객체들의 경계를 정의하는 역할을 한다.

---

## 애그리거트를 묶는 기준

애그리거트를 묶는 기준은 **도메인의 요구사항**이다.

특정 도메인 영역을 개발하기 위해 필요한 기능과 규칙을 정리했을 때,

그 규칙을 함께 만족해야 하는 객체들은 하나의 애그리거트로 묶을 수 있다.

쉽게 말해, 생명주기가 같거나 동일한 규칙을 공유하는 객체들을 한 애그리거트로 묶는다.

---

## 애그리거트 루트

모든 애그리거트에는 해당 도메인을 대표하는 **루트 엔티티**가 존재한다.

그것을 **애그리거트 루트**라고 부른다.

애그리거트 루트는 그 애그리거트를 대표하고, 외부에서 접근 가능한 유일한 객체이다.

(오로지 루트를 통해야만 내부 객체 접근 가능)

---

## 예시

아래와 같이 애그리거트로 묶어두었다고 보자.

```
User (루트)

Post (루트)
 └ Comment (내부 엔티티)
 └ Comment (내부 엔티티)
 └ Comment (내부 엔티티)
```

왜 Post가 루트이냐면,

- Comment는 혼자 존재할 수 없다. -> 반드시 특정 Post에 속해야한다.
- 게시글의 규칙은 Post가 관리해야한다.

  (게시글이 삭제되면 댓글도 삭제, 작성자만 삭제 가능)

  → 이 규칙을 Comment가 알아선 안된다.


### 잘못된 설계

```java
comment.setContent("수정");
comment.setPostId(3L);
```

위와 같이 설계 한다면, 다른 게시글로 댓글 이동이 가능하며,

게시글의 상태를 무시할수 있다. (검증 X)

### 올바른 설계

```java
post.addComment("내용");
post.removeComment(commentId);
post.delete();
```

위와 같이 설계한다면, Comment는 외부에서 직접 수정하지 못하며

반드시 Post를 통해서만 변경해야 하기 때문에

일관성 있게 관리 할 수 있다 (규칙을 깨지 않음).

---

## 애그리거트 간 연결

애그리거트끼리 연결 할때는 직접 객체를 참조하면 안되며

Id만 저장 하는듯이 느슨하게 연결해야한다.

그 이유는 다음과 같다.

1. **경계가 무너진다.**

   Post안에서 User의 상태까지 막 바꾸게 되면

   Post가 User까지 관리하게 되는 꼴이된다.

2. **트랜잭션이 커진다.**

   Post를 저장하려다 User도 바꾸고, 다른 것들도 바꾸다 보면

   한번의 작업의 범위가 커져 복잡 + 충돌 가능성이 증가한다.

3. **데이터가 항상 최신이 아닐 수 있다.**

   객체로 들고 있으면 이 User가 지금 최신 상태가 맞는지 모르는 문제 생긴다.

   Id로 다시 조회해서 필요할 때만 최신 상태를 가져오는게 안전하다.


보통은 Id로 연관 짓거나, 서비스 계층에서 순서를 조율한다.

---

## 정리

그렇기에 DDD는 한 트랜잭션에는 한 애그리거트만 다루는 것을 권장한다.

---


![Uploading 제목 없는 다이어그램.drawio.svg…]()


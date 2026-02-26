## Aggregate란?
한 번에 같이 바뀌어야 하는 도메인 객체들을 하나로 묶은 것이라고 정의할 수 있습니다.

## 왜 필요할까?
도메인 객체들을 막 연결해 두면 생기는 문제를 먼저 떠올려야 합니다.

`Order -> OrderLine -> Product -> Stock -> User ...` 이런 식으로 막 얽혀 있다고 가정하겠습니다.

그러면 어디서 상태를 바꿔야하는지 애매해지고, 여러 객체를 동시에 수정하다가 규칙이 깨지기 쉽습니다.

Aggregate는 이걸 막기 위해 "여기까지가 한 번에 맞춰야 하는 규칙 묶음"이라고 경계를 먼저 그어 버리는 설계 패턴이라고 볼 수 있습니다.

## 구성 요소
### Aggregate Root
외부에서는 Root에게만 요청하고, Root가 내부 Entity/Value Object를 조작합니다.

불변식/비즈니스 규칙을 여기서 체크하고 강제합니다.

### Entity
식별자(ID)가 있고, 상태가 바뀌어도 "같은 친구"로 취급되는 도메인 객체입니다.

Root말고도 Aggregate안에 여러 Entity가 있을 수 있습니다.

하지만, 이 친구들에 대한 변경은 항상 Root를 통해서 일어납니다.

### Value Object
ID가 필요없고, 값이 본체인 객체입니다.

불변으로 설계하는 게 일반적이고, 잘못되면 새로 만들어 바꿉니다.

## 규칙
- 외부에서 Aggregate 내부로 들어갈 수 있는 입구는 Root 하나뿐입니다.
  - Service가 내부 Entity를 직접 건드리지 않습니다.
- 다른 Aggregate는 ID로만 참조합니다.
  - A Aggregate가 B Aggregate의 객체를 필드로 들고 있지 말고, B의 ID만 가집니다.
  - "트랜잭션적으로는 끊고, 논리적으로만 연결"하는 구조가 됩니다.
- 하나의 트랜잭션에서 수정하는 Aggregate는 하나만 되도록 설계합니다.
  - 한 트랜잭션에서 여러 Aggregate를 바꾸고 싶어지면, Aggregate 설계가 잘못됐거나, 사가 패턴, 도메인 이벤트 같은 느슨한 협조 메커니즘이 필요한 상황일 가능성이 큽니다.

## 사진
<img width="667" height="505" alt="image" src="https://github.com/user-attachments/assets/7cd8fb22-cf87-4403-b979-ae9753c9a522" />

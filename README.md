# 연관
 - JPA 연습 (https://github.com/2jaeyeol/jpa-practice/blob/main/jpashop/JPASHOP.md)

# JPA
- Java Persistence API
- 자바 진영의 ORM 기술 표준
- 객체 답게 DB관리 유용
- 애플리케이션과 JDBC 사이에서 동작
- 표준 명세 인터페이스의 모음이다 (Hibernate , EclipseLink , DataNucleus)
- SQL 중심적인 개발에서 객체중심으로 개발 , 생산성 , 유지보수 , 데이터 접근 추상화와 벤더 독립성 , 표준 면에서 이점이 있음

- 패러다임 불일치
  - 상속 , 연관관계 , 객체 그래프 탐색 , 비교
  
- 성능
  - 1차 캐시와 동일성 보장 , 쓰기지연 , 지연로딩

# ORM
- Object-relational mapping(객체 관계 매핑)
- 객체는 객체대로 설계
- 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- ORM 프레임워크가 중간에서 매핑
- 대중적인 언어에는 대부분 ORM 기술이 있음

# JPA 동작
- ## 주의점
  - Entity Manager Factory 는 하나만 생성해서 애플리케이션 전체에서 공유
  - EntityManager는 쓰레드간에 공유x (사용하고 버려야 함.)
  - JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
 
# JPQL
- 일반 문자열
  - 동적쿼리 작성하기 힘듬
- JPA Creteria
  - 동적쿼리 작성은 편함
  - 유지보수가 힘듬 쿼리가 난잡함(유지보수 어려움)
- 쿼리DSL(동'적)


# 영속성 컨텍스트
- ## 이점
  - 1차캐시
  - 동일성 보장
  - 트랜잭션을 지원하는 쓰기 지연
  ```java
  //커밋하는 순간 SQL을 보낸다.
  transaction.commit();
  ```
  - 변경감지 , 지연로딩
- ## 상태
  - 비영속(new/transient) : 영속성 컨텐스트와 전혀 관계없는 새로운 상태
  ```java
  //객체생성(비영속)
  Member member = new Member();
  member.setId("member1");
  member.setUsename("회원1");
  ```
  - 영속(managed) : 영속성 컨텍스트에 관리되는 상태
  ```java
  Member member = new Member();
  member.setId("member1");
  member.setUsename("회원1");
  
  EntityManager em = emf.createEntityManager();
  em.getTransaction().begin();
  
  //객체저장(영속)
  em.persist(member);
  ```
  - 준영속(detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
  ```java
  //영속성 컨텍스트에서 분리
  em.detach(member);
  em.clear();
  em.close();
  ```
  - 삭제(removed) : 삭제된 상태
  ```java
  //삭제
  em.remove(member);
  ```
  
# 엔티티 매핑

# 연관관계 매핑


- ## 단방향 연관관계

<img src="https://media.vlpt.us/images/2jaeyeol/post/460bd167-1358-4c83-b11b-2924635fc38b/aaa.png"  width="700" height="370">

```java
@Entity
public class Member {

  //@Column(name = "TEAM_ID") 
  //private Long teamId;

  @ManyToOne
  @JoinColumn(name = "TEAM_ID")
  private Team team;
}
 ```
 
- ## 연관관계 저장
 ```java
 Team team = new Team();
 team.setName("TeamA");
 em.persist(team);
 
 Member member = new Member();
 member.setName("member1");
 member.setTeam(team);
 em.persist(member);
 
 //조회
 Member findMember = em.find(Member.class, member.getId());
 Team findTeam = findMember.getTeam();
 ```
 
- ## 양방향 연관관계

<img src="https://media.vlpt.us/images/2jaeyeol/post/506997c6-5df1-4a1f-8c76-78607a7594d1/%E1%84%8B%E1%85%A3%E1%86%BC%E1%84%87%E1%85%A1%E1%86%BC%E1%84%92%E1%85%A3%E1%86%BC.png"  width="700" height="370">

 ```java
@Entity
public class Member {

  //@Column(name = "TEAM_ID") 
  //private Long teamId;

  @ManyToOne
  @JoinColumn(name = "TEAM_ID")
  private Team team;
}

@Entity
public class Team {
  @OnetoMany(mappedBy = "team")
  List<Member> members = new ArrayList<>();
 } 
 
 //조회
 Team findTeam = em.find(Team.class, team.getId());
 int memberSize = findTeam.getMembers().size();
 ```
 
- ## 연관관계의 주인(외래키가 있는 곳을 주인으로 하는게 좋음)
  - 객체의 두 관계중 하나를 연관관계의 주인으로 지정
  - 연관관계의 주인만이 외래키를 관리(등록, 수정)
  - 주인이 아닌쪽은 읽기만 가능
  - 주인이 아니면 mappedBy
  
  
# 공통 Tips
### SQL 로그
- 외부라이브러리 : https://github.com/gavlyukovskiy/spring-boot-data-source-decorator 
  <br/>
  사용으로 sql 값 확인 가능

### 관계
- Entity 간 관계는 m:m 은 사용하지 말자 주로 ManyToOne 사용을 권장
  - 중간 테이블에 다른 컬럼을 추가할 수 없고 세밀하게 쿼리를 실행하기 힘들다. 중간엔티티를 추가해 OneToMany ,ManyToOne 를 사용하자.

### @Getter @Setter
- @Getter는 모두 열어두는 것이 편리하다.
  - 조회를 많이 한다해도 데이터의 변경이 일어나지 않는다.)
- @Setter의 경우 꼭 필요한 곳에서만 사용하도록 한다. 
  - 도대체 왜 변경되는지 추적이 불가능 할 수 있다.
  - @Setter 대신 변경 지점이 명확하도록 비즈니스 메서드를 별도 제공하는 것이 좋다
  - 값타입을 변경 불가능하게 만들자
  ```java
  //ex) protected 사용
  protected Address(){

  }
  ```

### 식별자
- 관례상 (테이블명+id)로 하는 것이 좋다(일관성) 

### 지연로딩
- 즉시로딩 `Eager`는 예측이 어렵고 어떤 SQL이 실행될지 추적이 어렵다. 특히 JPQL을 사용할 때에 N+1 문제가 발생한다.
- 지연로딩 `LAZY`를 사용하자.
- 연관된 엔티티를 DB에서 함꼐 조회 한다고 하면 fetch join 과 엔티티 그래프 기능을 사용하자.
- @XToOne 관계는 기본이 즉시로딩이므로 직접 `LAZY`로 설정해야함.

### 컬렉션 초기화
- 필드에서 바로 초기화 하는 것이 안전하다.
  - null 문제에서 안전
  - 하이버네이트는 엔티티를 영속화 할 때, 컬랙션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다. 만약 임의의 메서드에서 컬력션을 잘못 생성하면 하이버네이트 내부 메커니즘에 문제가 발생할 수 있다.
  ```java
  List<Order> orders = new List<>();
  ```

### 테이블 컬럼명 매핑
- 논리명 생성: 명시적으로 컬럼, 테이블명을 직접 적지 않으면 ```ImplicitNamingStrategy``` 사용
```spring.jpa.hibernate.naming.implicit-strategy``` : 테이블이나, 컬럼명을 명시하지 않을 때 논리명
적용,
- 물리명 적용: ```spring.jpa.hibernate.naming.physical-strategy``` : 모든 논리명에 적용됨, 실제 테이블에 적용
(username usernm 등으로 회사 룰로 바꿀 수 있음)



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
--

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
<img src="./Users/jaeyeol/Desktop/aaa.png"  width="700" height="370">

- ## 단방향 연관관계
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
  List<Member> members = new ArrayList<Member>();
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

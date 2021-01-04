# JPASHOP
<br/>

# 목차
 - [DB 객체 관계 구조](#db-객체-관계-구조)
 - [Member](#member)
 - [Order](#order)
 - [Delivery](#delivery)
 - [OrderItem](#orderitem)
 - [Item](#item)
 - [Category](#category)
 - [Address](#address)
 - [공통 Tips](#공통-tips)
<br/><br/>

# DB 객체 관계 구조
<img src="https://media.vlpt.us/images/2jaeyeol/post/0e1d62a6-4604-490c-872b-fb9b99edd8a7/jpashop%20db.png" width = "700" height="370">
<br/><br/><br/><br/>

# Member
- ## 객체관계

 | name | required|key|description|DB|name|required|key|
 |--|--|--|--|--|--|--|--|
 | id | Long | PK |||member_id|BIGINT|PK|
 | name | String | |이름||name|VARCHAR(255)||
 | address | Address || 주소||city|VARCHAR(255)||
 | orders | List | |주문내역||street|VARCHAR(255)||
 ||||||zipcode|VARCHAR(255)||


# Order
- ## 객체관계

| name | required|key|description|DB|name|required|key|
 |--|--|--|--|--|--|--|--|
 | id | Long | PK |||order_id|BIGINT|PK|
 | member | Member | |주문자||member_id|BIGINT|FK|
 | orderitems | List || 주문제품||delivery_id|BIGINT|FK|
 | delivery | Delivery | |배송지||order_date|DATETIME(6)||
 | orderDate | Date | |주문날짜||status|VARCHAR(255)||
 | status | OrderStatus | |주문상태|||||
# Delivery
- ## 객체관계

| name | required|key|description|DB|name|required|key|
 |--|--|--|--|--|--|--|--|
 | id | Long | PK |||delivery_id|BIGINT|PK|
 | order | Order | |주문||city|VARCHAR(255)||
 | address | Address || 배송주소||street|VARCHAR(255)||
 | status | Delivery | |배송상태||zipcode|VARCHAR(255)||
 ||||||status|VARCHAR(255)||

# OrderItem
- ## 객체관계

| name | required|key|description|DB|name|required|key|
 |--|--|--|--|--|--|--|--|
 | id | Long | PK |||order_item_id|BIGINT|PK|
 | item | Item | |주문제품상세||item_id|BIGINT|FK|
 | order | Order || 주문||order_id|BIGINT|FK|
 | orderPrice | Int | |주문가격||order_price|int||
 | count | Int | |주문수량||count|int||
 
# Item
- ## 객체관계

| name | required|key|description|DB|name|required|key|
 |--|--|--|--|--|--|--|--|
 | id | Long | PK |||item_id|BIGINT|PK|
 | name | String | |제품이름||name|VARCHAR(255)||
 | price | Int || 주문||price|INT||
 | stockQuantity | Int | |재고수량||stock_quantity|INT||
 ||||||artist|VARCHAR(255)||
 ||||||etc|VARCHAR(255)||
 ||||||author|VARCHAR(255)||
 ||||||isbn|VARCHAR(255)||
 ||||||actor|VARCHAR(255)||
 ||||||director|VARCHAR(255)||
 ||||||dtype|VARCHAR(31)||
 
 <br/>


- ## 상속
### 1. Album
| name | required|key|description|
 |--|--|--|--|
 | artist | String |  | 아티스트|
 | etc | String | |기타|
</br>

### 2. Book
| name | required|key|description|
 |--|--|--|--|
 | author | String | |작가|
 | isbn | String | |ISBN|
</br>

### 3. Movie
| name | required|key|description|
 |--|--|--|--|
 | director | String |  |감독|
 | actor | String | |배우|
</br>

# Category
- ## 객체관계

| name | required|key|description|DB|name|required|key|
 |--|--|--|--|--|--|--|--|
 | id | Long | PK |||category_id|BIGINT|PK|
 | name | String | |카테고리명||name|VARCHAr(255)||
 | items | List | |아이템목록||parent_id|BIGINT|FK|
 | parent | Category | |본인상속|||||
 | child | List | |본인상속|||||
</br>

# Address              
- ## 객체관계 <t/> 

| name | required|key|description|
 |--|--|--|--|
 | city | String |  |지역명1|
 | street | String | |지역명2|
 | zipcode | String ||지역명3|

</br>


# 공통 Tips
### Domain
  - 도메인 주도설계
      - 도메인 자체에서 해결할 수 있는 로직을 도메인에 추가
      - ex) @Setter를 쓰지 않고 핵심 비즈니스 로직을 이용하여 변경하는 것
        ```java
        //ex) 재고수량
        public void addStock(int quantity){
          this.stockQuantity += quantity;
        } 
        ```

### Repository
  - @PersistenceContext - EntityManager 주입
    ```java
    @PersistenceContext
    private EntityManager em;
    ```

### Service
  - @Transactional - 트랜잭션 영속성 컨텍스트
    - readOnly = true 읽기 전용 메서드에서 사용 영속성 컨텍스트에 플러쉬 하지 않으므로 약간의 성능 향상 , 주로 읽기전용에 변경 메서드가 있으면 따로 @Transactional을 추가해주면 됨


  - @Autowired - 생성자 Injection - 초기화 문제 해결
    ```java
    private final MemberRepogitory memberRepogitory
    
    @Autowired
    //테스트에서 임의의 memberRepogitory를 넣어주기 편함
    public MemberService(MemberRepogitory memberRepogitory){
      this.memberRepogitory = memberRepogitory;
    }

    //@RequiredArgConstructor 사용법 (위와 같다.) 
    //Repogitory의 EntityManager 도 사용가능
    @RequiredArgsConstructor
    public class ItemService {
      private final MemberRepogitory memberRepogitory
    ```

### Controller
  - 스프링 MVC가 제공하는 모델객체를 사용


### Form class
  - Entity를 서비스계층과 화면계층으로 명확하게 분리한다.
  - Entity는 핵심 비즈니스 로직만 가지고 있어야 하고, 화면을 위한 로직은 없어야 한다.(엔티티는 최대한 순수하게 유지)
  - 화면이나 API에 맞는 폼 객체나 DTO를 사용하는게 맞다

### 변경감지와 병합(merge)

  - 준영속 엔티티 ex) Book 객체는 이미 한번 DB에 저장되어서 식별자가 존재함
  - 준영속 엔티티 수정
    - 변경감지
      - 영속성 컨텍스트에서 엔티티를 다시 조회한 후 데이터를 수정하는 방법
      ```java
      @Transactional
      void update(Item itemParam) { //itemParam: 파리미터로 넘어온 준영속 상태의 엔티티
      Item findItem = em.find(Item.class, itemParam.getId()); //같은 엔티티를 조회한 다.
      findItem.setPrice(itemParam.getPrice()); //데이터를 수정한다. }
      ```
      
    - 병합(merge)사용
      - 준영속 상태를 다시 영속상태로 변환
      ```java
      @Transactional
      void update(Item itemParam) { //itemParam: 파리미터로 넘어온 준영속 상태의 엔티티 
      Item mergeItem = em.merge(item);
      }
      ```
    - 동작 방식
      1. 준영속 엔티티의 식별자 값으로 영속 엔티티를 조회한다.
      2. 영속 엔티티의 값을 준영속 엔티티의 값으로 모두 교체한다.(병합한다.)
      3. 트랜잭션 커밋 시점에 변경 감지 기능이 동작해서 데이터베이스에 UPDATE SQL이 실행

  - <u>__변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합을 사용하면 모든 속성이 변경된다. 병합시 값이 없으면 null 로 업데이트 할 위험도 있다. (병합은 모든 필드를 교체한다.)__</u>
  - 변경감지 기능을 사용하는 것이 좋다.

### 이해 안되는 것
  - ItemRepogitory 에서 save() 와 MemberRepogitory의 save() 차이
  ```java
  //ItemRepogitory id가 지정되지 않으면 id를 넣어주고 아니면 업데이트?
      public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        }else {
            em.merge(item);
        }
    }
  //MemberRepogitory
   public void save(Member member){
        em.persist(member);
    }
  ```

  - save() 메서드는 식별자 값이 없으면(null) 새로운 메서드로 판단해서 영속화하고 식별자가 있으면 병합한다.
  - 준영속 상태라면 id값이 있기 때문에 병합한다.
  - 신규 데이터의 저장 뿐만이 아닌 기존의 데이터의 변경 저장도 의미 



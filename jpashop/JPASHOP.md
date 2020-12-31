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

### Repogitory
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

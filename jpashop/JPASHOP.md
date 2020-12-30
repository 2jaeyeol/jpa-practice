# JPASHOP
<br/>

# 목차
 - [DB 객체 관계 구조](#db-객체-관계-구조)
 - [Member](#member)
 - [Order](#order)
 - [Delivery](#Delivery)
 - [OrderItem](#Orderitem)
 - [Item](#item)
 - [Category](#category)
 - [Address](#address)
<br/><br/>

# DB 객체 관계 구조
<img src="https://media.vlpt.us/images/2jaeyeol/post/0e1d62a6-4604-490c-872b-fb9b99edd8a7/jpashop%20db.png" width = "700" height="370">
<br/><br/><br/><br/>

# Member
- ## 객체관계

 | name | required|key|description|
 |--|--|--|--|
 | id | Long | PK ||
 | name | String | |이름|
 | address | Address || 주소|
 | orders | List | |주문내역|


# Order
- ## 객체관계

| name | required|key|description|
 |--|--|--|--|
 | id | Long | PK ||
 | member | Member | |주문자|
 | orderitems | List || 주문제품|
 | delivery | Delivery | |배송지|
 | orderDate | Date | |주문날짜|
 | status | OrderStatus | |주문상태|
# Delivery
- ## 객체관계

| name | required|key|description|
 |--|--|--|--|
 | id | Long | PK ||
 | order | Order | |주문|
 | address | Address || 배송주소|
 | status | Delivery | |배송상태|

# OrderItem
- ## 객체관계

| name | required|key|description|
 |--|--|--|--|
 | id | Long | PK ||
 | item | Item | |주문제품상세|
 | order | Order || 주문|
 | orderPrice | Int | |주문가격|
 | count | Int | |주문수량|
 
# Item
- ## 객체관계

| name | required|key|description|
 |--|--|--|--|
 | id | Long | PK ||
 | name | String | |제품이름|
 | price | Int || 주문|
 | stockQuantity | Int | |재고수량|
 | categories | List | |카테고리|
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

| name | required|key|description|
 |--|--|--|--|
 | id | Long | PK ||
 | name | String | |카테고리명|
 | items | List | |아이템목록|
 | parent | Category | |본인상속|
 | child | List | |본인상속|
</br>

# Address
- ## 객체관계

| name | required|key|description|
 |--|--|--|--|
 | city | String |  |지역명1|
 | street | String | |지역명2|
 | zipcode | String ||지역명3|

</br>

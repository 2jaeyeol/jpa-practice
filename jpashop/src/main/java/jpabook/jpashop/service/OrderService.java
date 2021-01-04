package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repogitory.ItemRepogitory;
import jpabook.jpashop.repogitory.MemberRepogitory;
import jpabook.jpashop.repogitory.OrderRepogitory;
import jpabook.jpashop.repogitory.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepogitory orderRepogitory;
    private final MemberRepogitory memberRepogitory;
    private final ItemRepogitory itemRepogitory;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티조회
        Member member = memberRepogitory.findOne(memberId);
        Item item = itemRepogitory.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepogitory.save(order);

        return order.getId();

    }

    //취소
    @Transactional
    public void cancelOrder(Long orderid) {
        Order order = orderRepogitory.findOne(orderid);
        order.cancel();
    }

    //    검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepogitory.findAllByCriteria(orderSearch);
    }
}


package jpabook.jpashop.repogitory.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepogitory {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new jpabook.jpashop.repogitory.order.simplequery.OrderSimpleQueryDto(o.id, m.name,\n" +
                        "o.orderDate, o.status, d.address) " +
                        " from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", OrderSimpleQueryDto.class
        ).getResultList();
    }
}

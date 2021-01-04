package jpabook.jpashop.service;


import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repogitory.ItemRepogitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepogitory itemRepogitory;

    @Transactional
    public void saveItem(Item item){
        itemRepogitory.save(item);
    }

    public List<Item> findAll(){
        return itemRepogitory.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepogitory.findOne(itemId);
    }

    public List<Item> findItems() {
        return itemRepogitory.findAll();
    }

    @Transactional
    public void updateItem(Long id, String name, int price) {
        Item item = itemRepogitory.findOne(id); item.setName(name); item.setPrice(price);
    }
}

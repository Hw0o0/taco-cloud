package com.tacos.tacocloud.repository;

import com.tacos.tacocloud.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    //Order save(Order order);
}

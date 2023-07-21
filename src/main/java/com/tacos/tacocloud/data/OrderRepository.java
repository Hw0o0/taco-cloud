package com.tacos.tacocloud.data;

import com.tacos.tacocloud.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    //Order save(Order order);
}

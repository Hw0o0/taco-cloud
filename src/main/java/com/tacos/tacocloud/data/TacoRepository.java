package com.tacos.tacocloud.data;

import com.tacos.tacocloud.domain.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco,Long> {
    //Taco save(Taco design);
}
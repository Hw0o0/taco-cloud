package com.tacos.tacocloud.repository;

import com.tacos.tacocloud.Taco;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco,Long> {
    //Taco save(Taco design);
}
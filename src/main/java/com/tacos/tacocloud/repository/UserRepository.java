package com.tacos.tacocloud.repository;

import com.tacos.tacocloud.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User,Long> {
    User findByUsername(String username);
}

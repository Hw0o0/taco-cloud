package com.tacos.tacocloud.data;

import com.tacos.tacocloud.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User,Long> {
    User findByUsername(String username);
}

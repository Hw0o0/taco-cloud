package com.tacos.tacocloud.security;

import com.tacos.tacocloud.domain.User;
import com.tacos.tacocloud.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override // loadUserByUsername() 메소드에서는 절대로 null을 반환하지 않는다는 규칙이있다. 그래서 null이 나오면 usernameNotFoundException을 발생한다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User'"+ username +"' not found");
    }
}

package com.conferences.service.implementation;

import com.conferences.entity.User;
import com.conferences.model.Account;
import com.conferences.repository.IUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AccountService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    public AccountService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Searching user by login");
        User user = userRepository.findByLogin(username);
        if (user == null) {
            log.warn("User now found");
            throw new UsernameNotFoundException(username + " not found");
        }
        return new Account(user);
    }
}

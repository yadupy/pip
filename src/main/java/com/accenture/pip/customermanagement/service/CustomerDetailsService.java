/*
package com.accenture.pip.customermanagement.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@Slf4j
public class CustomerDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("method loadUserByUsername called");
        UserDetails user =
                User.builder()
                        .username("prateek")
                        .password("Angular2023")
                        .roles("USER")
                        .build();

        return user;
    }
}

*/

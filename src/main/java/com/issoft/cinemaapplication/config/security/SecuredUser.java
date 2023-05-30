package com.issoft.cinemaapplication.config.security;

import com.issoft.cinemaapplication.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class SecuredUser extends org.springframework.security.core.userdetails.User {
    public SecuredUser(final User user) {
        super(user.getLogin(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getSystemRole().getName())));
    }
}

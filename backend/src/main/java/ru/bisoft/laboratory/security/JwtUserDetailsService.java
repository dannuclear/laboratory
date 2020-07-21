package ru.bisoft.laboratory.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.auth.User;
import ru.bisoft.laboratory.service.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Пользователь с именем " + username + " не найден");
		/*
		org.springframework.security.core.userdetails.User.builder()//
				.username(user.getUsername())//
				.password(user.getPassword())//
				.authorities(user.getAuthorities())//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();*/
        return user;
    }
}

package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.auth.User;
import ru.bisoft.laboratory.repository.UserRepository;
import ru.bisoft.laboratory.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @PreAuthorize("hasAuthority('USER_WRITE') or hasAnyRole('USER_ADMIN')")
    public User save(User user) {
        User res = userRepository.save(user);
        return res;
    }

    @Override
    @PreAuthorize("hasAuthority('USER_WRITE') or hasAnyRole('USER_ADMIN')")
    public User create(String username) {
        User user = new User();
        user.setUsername(username);
        user.setGroups("USER_ROLE");
        return user;
    }

    @Override
    @PreAuthorize("hasAuthority('USER_READ') or hasRole('USER_ADMIN')")
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('USER_READ') or hasAnyRole('USER_ADMIN')")
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('USER_WRITE') or hasAnyRole('USER_ADMIN')")
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User register(User user) {
        if (user.getGroups() == null)
            user.setGroups("USER_ROLE");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User registeredUser = userRepository.save(user);
        return registeredUser;
    }

    @Override
    @PreAuthorize("hasAuthority('USER_WRITE') or hasAnyRole('USER_ADMIN')")
    public User create() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @PreAuthorize("hasAuthority('USER_WRITE') or hasAnyRole('USER_ADMIN')")
    public Iterable<User> saveAll(Iterable<User> entities) {
        return userRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('USER_READ') or hasAnyRole('USER_ADMIN')")
    public Page<User> findByString(String value, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }
}

package ru.bisoft.laboratory.service;

import ru.bisoft.laboratory.domain.auth.User;

public interface UserService extends GuideService<User> {

	User create(String username);

	User findByUsername(String username);

	User register(User user);
}

package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.laboratory.domain.auth.User;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

	@EntityGraph(value = "user.allJoins")
	User findByUsername(String username);

	@Override
	@EntityGraph(value = "user.allJoins")
	Page<User> findAll(Pageable pageable);
}

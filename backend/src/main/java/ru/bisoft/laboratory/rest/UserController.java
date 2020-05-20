package ru.bisoft.laboratory.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.bisoft.laboratory.domain.auth.User;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/{user}")
	public ResponseEntity<User> findOne(@PathVariable User user) {
		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<PagedModel<User>> findAll(Pageable pageable) {
		Page<User> page = userService.findAll(pageable);
		return ResponseEntity.ok(PagedModel.wrap(page));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('USER_WRITE')")
	public void save(@RequestBody User newUser) {
		newUser.setId(null);
		userService.save(newUser);
	}

	@PutMapping("/{user}")
	public void save(@PathVariable User user, @RequestBody User newUser) {
		newUser.setId(user.getId());
		userService.save(newUser);
	}

	@DeleteMapping("/{user}")
	public void delete(@PathVariable User user) {
		userService.delete(user);
	}
}

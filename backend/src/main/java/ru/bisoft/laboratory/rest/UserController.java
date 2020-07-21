package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

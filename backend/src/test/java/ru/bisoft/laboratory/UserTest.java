package ru.bisoft.laboratory;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.bisoft.laboratory.domain.auth.Privilege;
import ru.bisoft.laboratory.domain.auth.Role;
import ru.bisoft.laboratory.domain.auth.User;
import ru.bisoft.laboratory.service.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/spring/application-context.xml", "file:WebContent/WEB-INF/spring/jpa/jpa-context.xml", "file:WebContent/WEB-INF/spring/security/security-context.xml" })
public class UserTest {

	@Autowired
	private UserService userService;

	@Test
	public void create() {
		User user = new User();
		user.setUsername("testUser");
		user.setPassword("password1");
		Role role = new Role("ADMIN");
		role.setPrivileges(new HashSet<Privilege>(Arrays.asList(Privilege.READ, Privilege.WRITE)));
		user.setRoles(new HashSet<Role>(Arrays.asList(role)));
		//userService.save(user);

		//user = userService.findByUsername("testUser");
		//Assertions.assertTrue(user.getRoles());
		Assertions.assertNotNull(user);
		// userService.delete(user);
	}
}

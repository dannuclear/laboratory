package ru.bisoft.laboratory.rest;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.bisoft.laboratory.domain.Session;
import ru.bisoft.laboratory.domain.auth.User;
import ru.bisoft.laboratory.dto.AuthenticationRequestDto;
import ru.bisoft.laboratory.security.jwt.JwtTokenProvider;
import ru.bisoft.laboratory.service.UserService;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
@PreAuthorize("permitAll")
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;
	private final DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);

	private Map<UUID, Session> sessions = new HashMap<>();

	@RequestMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto request) {
		try {
			String username = request.getUsername();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));

			User user = userService.findByUsername(username);

			if (user == null) {
				throw new UsernameNotFoundException("Пользователь не найден");
			}
			Map<String, String> responseMap = new HashMap<>();

			String accessToken = jwtTokenProvider.createToken(user);
			UUID refreshToken = UUID.randomUUID();

			responseMap.put("username", username);
			responseMap.put("perms", user.getGroups());

			responseMap.put("accessToken", accessToken);
			Date accessValidaty = new Date(new Date().getTime() + jwtTokenProvider.getAccessTimeout());
			responseMap.put("accessExp", dateTimeFormat.format(accessValidaty));

			responseMap.put("refreshToken", refreshToken.toString());
			Date refrestValidaty = new Date(new Date().getTime() + jwtTokenProvider.getRefreshTimeout());
			responseMap.put("refreshExp", dateTimeFormat.format(refrestValidaty));

			responseMap.put("initial", user.getEmployeeFullName());
			responseMap.put("organization", user.getOrganizationName());
			
			Session session = Session.builder().username(username).expAt(refrestValidaty).build();
			sessions.put(refreshToken, session);

			return ResponseEntity.ok(responseMap);
		} catch (Exception e) {
			Map<Object, Object> responseMap = new HashMap<>();
			responseMap.put("Authentication", false);
			return ResponseEntity.ok(responseMap);
			// throw new BadCredentialsException("Неверные имя пользователя или пароль");
		}
	}

	@RequestMapping("/refresh-token")
	public ResponseEntity<?> login(@RequestBody Map<Object, Object> params) {
		try {
			Map<Object, Object> responseMap = new HashMap<>();
			UUID oldRefreshToken = Optional.ofNullable((String) params.get("refreshToken")).map(UUID::fromString).orElse(null);
			Session session = sessions.get(oldRefreshToken);
			if (session == null || session.getExpAt().before(new Date())) {
				responseMap.put("Authentication", false);
			} else {
				User user = userService.findByUsername(session.getUsername());
				if (user == null) {
					throw new UsernameNotFoundException("Пользователь не найден");
				}
				String accessToken = jwtTokenProvider.createToken(user);
				UUID newRefreshToken = UUID.randomUUID();

				responseMap.put("username", session.getUsername());
				responseMap.put("perms", user.getGroups());

				responseMap.put("accessToken", accessToken);
				Date accessValidaty = new Date(new Date().getTime() + jwtTokenProvider.getAccessTimeout());
				responseMap.put("accessExp", dateTimeFormat.format(accessValidaty));

				responseMap.put("refreshToken", newRefreshToken.toString());
				responseMap.put("refreshExp", dateTimeFormat.format(session.getExpAt()));
				sessions.remove(oldRefreshToken);
				sessions.put(newRefreshToken, session);
			}
			return ResponseEntity.ok(responseMap);
		} catch (Exception e) {
			Map<Object, Object> responseMap = new HashMap<>();
			responseMap.put("Authentication", false);
			return ResponseEntity.ok(responseMap);
		}
	}
}

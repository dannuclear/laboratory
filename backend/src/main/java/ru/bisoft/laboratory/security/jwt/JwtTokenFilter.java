package ru.bisoft.laboratory.security.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.bisoft.laboratory.rest.handler.ApiError;

public class JwtTokenFilter extends GenericFilterBean {

	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		if (token != null && jwtTokenProvider.isValidToken(token)) {
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} else if (token != null) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			// httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid
			// authentication.");
			response.setContentType("application/json");
			ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Invalid token", "Invalid token");
			ObjectMapper mapper = new ObjectMapper();
			PrintWriter out = httpResponse.getWriter();
			out.print(mapper.writeValueAsString(apiError));
			out.flush();
		}
		chain.doFilter(request, response);
	}
}

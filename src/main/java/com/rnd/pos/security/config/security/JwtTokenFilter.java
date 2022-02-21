package com.rnd.pos.security.config.security;

import static org.springframework.util.StringUtils.hasLength;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rnd.pos.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtTokenUtil;
	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (null != header) {
			if (hasLength(header) && !header.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}

			// Get jwt token and validate
			final String token = header.split(" ")[1].trim();
			if (!jwtTokenUtil.validate(token)) {
				filterChain.doFilter(request, response);
				return;
			}

			// Get user identity and set it on the spring security context
			UserDetails userDetails = userRepository.findByUsername(jwtTokenUtil.getUsername(token)).orElse(null);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails == null ? List.of() : userDetails.getAuthorities());

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
}
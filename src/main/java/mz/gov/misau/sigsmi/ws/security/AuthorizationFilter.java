package mz.gov.misau.sigsmi.ws.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import mz.gov.misau.sigsmi.ws.SpringApplicationContext;
import mz.gov.misau.sigsmi.ws.service.UserService;
import mz.gov.misau.sigsmi.ws.shared.dto.RoleDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.UserDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.UserLevelDTO;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String header = request.getHeader(SecurityConstants.TOKEN_HEADER_STRING);

		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.TOKEN_HEADER_STRING);

		if (token != null) {
			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

			String user = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret()).parseClaimsJws(token)
					.getBody().getSubject();
			if (user != null) {

				UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
				UserDTO userDTO = userService.findUserByEmail(user);

				final Collection<GrantedAuthority> authorities = new ArrayList<>();
				userDTO.getGroups().forEach(new Consumer<UserLevelDTO>() {
					@Override
					public void accept(UserLevelDTO group) {
						group.getRoles().forEach(new Consumer<RoleDTO>() {
							@Override
							public void accept(RoleDTO role) {
								SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
								authorities.add(authority);
							}
						});
					}
				});

				return new UsernamePasswordAuthenticationToken(user, userDTO.getEncryptedPassword(), authorities);
			}
			return null;
		}
		return null;
	}
}

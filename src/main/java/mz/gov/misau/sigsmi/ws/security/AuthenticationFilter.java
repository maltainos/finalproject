package mz.gov.misau.sigsmi.ws.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mz.gov.misau.sigsmi.ws.SpringApplicationContext;
import mz.gov.misau.sigsmi.ws.service.UserService;
import mz.gov.misau.sigsmi.ws.shared.MyUtils;
import mz.gov.misau.sigsmi.ws.shared.dto.RoleDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.UserDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.UserLevelDTO;
import mz.gov.misau.sigsmi.ws.ui.model.request.UserLoginRequestDetails;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	
	
	private UserDTO userDto;
	private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			
			UserLoginRequestDetails creds = new ObjectMapper().readValue(request.getInputStream(),
					UserLoginRequestDetails.class);

			UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
			UserDTO user = userService.findUserByEmail(creds.getEmail());
			userDto = user;

			authorities = new ArrayList<>();
			user.getGroups().forEach(new Consumer<UserLevelDTO>() {
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

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), authorities));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication auth) throws IOException, ServletException {

		String userName = ((User) auth.getPrincipal()).getUsername();

		String access_token = Jwts.builder().setSubject(userName)
				.claim("userId", userDto.getUserId())
				.claim("firstName", userDto.getFirstName())
				.claim("lastName", userDto.getLastName())			
				.claim("authorities", authorities)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
		
		String refresh_token = Jwts.builder().setSubject(userName)
				.claim("userId", userDto.getId())
				.claim("firstName", userDto.getFirstName())
				.claim("lastName", userDto.getLastName())
				.claim("authorities", authorities)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.REFRESH_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
		
		response.addHeader(SecurityConstants.TOKEN_HEADER_STRING, SecurityConstants.TOKEN_PREFIX + access_token);
		response.addHeader(SecurityConstants.REFRESH_TOKEN_HEADER_STRING, SecurityConstants.TOKEN_PREFIX + refresh_token);
		response.addHeader("UserId", userDto.getUserId());
		
		Map<String, Object> tokens = new HashMap<>();
		tokens.put("token_type", SecurityConstants.TOKEN_PREFIX);
		tokens.put("access_token",SecurityConstants.TOKEN_PREFIX + access_token);
		tokens.put("expiration_time", MyUtils.getExpirationTimeToken(access_token));
		tokens.put("refresh_token", SecurityConstants.TOKEN_PREFIX + refresh_token);
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}
}

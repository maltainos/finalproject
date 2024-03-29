
package mz.gov.misau.sigsmi.ws.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import mz.gov.misau.sigsmi.ws.service.UserService;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private UserService userDetails;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(UserService userDetails, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetails = userDetails;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http
		.cors().and().csrf()
		.disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL)
		.permitAll()
		.antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
		.permitAll()
		.antMatchers(HttpMethod.GET, SecurityConstants.DOCS_UP_URL)
		.permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilter(getAuthenticationFilter())
		.addFilter(new AuthorizationFilter(authenticationManager()))
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	/*
		http
		.cors().and().csrf()
		.disable();
	*/
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder);
	}

	// Change default url authentication
	public AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/users/login");
		return filter;
	}

}

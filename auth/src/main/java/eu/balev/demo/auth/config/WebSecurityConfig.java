package eu.balev.demo.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Standard web security configuration.
 * 
 * @author LBalev
 */
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder)
			throws Exception {
		//Registers our user details service and the password encoder
		authManagerBuilder
				.userDetailsService(userDetailsService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean(name = "authenticationManagerBean")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	       return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// this is just for the H2 DB and testing purposes
		// TODO: should be disabled in real app
		http.
			authorizeRequests().antMatchers("/h2-console/**").permitAll().//h2 console
			and().
			authorizeRequests().anyRequest().authenticated();//the rest
		
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().disable();
	}
}

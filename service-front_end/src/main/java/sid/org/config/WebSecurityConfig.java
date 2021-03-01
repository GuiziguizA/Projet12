package sid.org.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * configuration de spring security
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.addFilterBefore(new TwoFactorAuthenticationFilter((authenticationManagerBean())),
				UsernamePasswordAuthenticationFilter.class).authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/webjars/**", "/userForm").permitAll()
				.antMatchers(HttpMethod.GET, "/userForm").permitAll().antMatchers(HttpMethod.POST, "/user").permitAll()
				.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
				.defaultSuccessUrl("/home").and().logout().logoutSuccessUrl("/login");

	}

}
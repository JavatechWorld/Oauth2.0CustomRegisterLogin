package com.oauth.implementation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.oauth.implementation.service.DefaultUserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Autowired
	private  DefaultUserService userDetailsService;
	
	@Autowired
	AuthenticationSuccessHandler successHandler;

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
	
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/registration/**", "/login/**").permitAll();
                    requests.anyRequest().authenticated();})
                .formLogin(login -> login.loginPage("/login").successHandler(successHandler)).csrf(csrf -> csrf.disable())
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login")).oauth2Login(login -> login.loginPage("/login").successHandler(successHandler));
return http.build();

    }

}

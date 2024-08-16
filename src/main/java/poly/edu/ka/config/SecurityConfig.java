package poly.edu.ka.config;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import poly.edu.ka.domain.Account;
import poly.edu.ka.services.AccountService;


@Configuration
public class SecurityConfig{
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
//
//	
//	@Autowired
//	private AccountService accountService;
//
//	
	
	
	@Bean
	BCryptPasswordEncoder getBCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
//		
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
////		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
////		
////		auth.inMemoryAuthentication()
////		.withUser("ka03").password(pe.encode("123")).roles("admin").and()
////		.withUser("ka04").password(pe.encode("123")).roles("user");
////	
//	auth.userDetailsService(username -> {
//		try {
//			Optional<Account> user = accountService.findById(username);
//			String password = passwordEncoder.encode(user.getPassword());
//			String[] roles = user.getAuthorities().stream()
//					.map(er -> er.getRole().getId())
//					.toArray(String[]::new);
//
//			return User.withUsername(username).password(password).roles(roles).build();
//		} catch (NoSuchElementException e) {
//			throw new UsernameNotFoundException(username + " not found!");
//		}
//	});
//	}
//	@Override
//	protected void configure(HttpSecurity http) throws Exception
//	{
////		http.csrf().disable().cors().disable();
////		http.authorizeRequests().anyRequest()
////		.authenticated();
//		http
//        .authorizeRequests()
//            .antMatchers("site/**").permitAll() // Các URL public
//            .anyRequest().authenticated() // Tất cả các request cần được xác thực
//            .and()
//        .formLogin()
//            .loginPage("/alogin") // Đường dẫn tới trang đăng nhập tùy chỉnh
//            .permitAll()
//            .and()
//        .logout()
//            .permitAll();
//	}

}

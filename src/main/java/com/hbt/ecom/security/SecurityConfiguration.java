package com.hbt.ecom.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    throws Exception {
    return httpSecurity.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll())
      .userDetailsService(userDetailsService())
      .csrf(AbstractHttpConfigurer::disable)
      .build();
  }

  private UserDetailsService userDetailsService() {
    UserDetails userDetails = User.withUsername("dummyUser")
                                  .password("dummyPassword")
                                  .roles("USER")
                                  .build();

    return new InMemoryUserDetailsManager(userDetails);
  }
}

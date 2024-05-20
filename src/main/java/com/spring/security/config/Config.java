package com.spring.security.config;

import com.spring.security.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Config {
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("SecurityFilterChain");
        return httpSecurity.
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(
                        req -> {
                            req.requestMatchers("/home","/register/**").permitAll();
                            req.requestMatchers("/admin/**").hasRole("ADMIN");
                            req.requestMatchers("/user/**").hasRole("USER");
                            req.anyRequest().authenticated();
                        })
                .formLogin(httpSecurityFormLoginConfigurer ->{
                    httpSecurityFormLoginConfigurer.loginPage("/login")
                    .permitAll();
                }).build();
    }

    //    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails normalUser= User.builder().
//                username("venkat")
//                        .password("$2a$12$Um9/XfDCi.DDot9VyUEaju1DWoJLEIbb.cBpVWP3/4QMNJMzmAe6a")
//                                .roles("USER").build();
//        UserDetails adminUser= User.builder().
//                username("venkatbasa")
//                .password("$2a$12$xYjEuzNlKvhG6iZsl3C40u71M8mZ.gsTpyvXuX5COLUcTZZk.CLpi")
//                .roles("ADMIN","USER").build();
//        return new InMemoryUserDetailsManager(adminUser,normalUser);
//    }
    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("UserDetailsService");
        return myUserDetailService;
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        System.out.println("AuthenticationProvider");
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(myUserDetailService);
        auth.setPasswordEncoder(passwordEncoder());
        System.out.println(auth);
        return auth;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
}

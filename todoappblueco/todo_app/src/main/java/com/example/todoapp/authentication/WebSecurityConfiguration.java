package com.example.todoapp.authentication;

//import com.example.todoapp.utils.ICheckBCryptPasswordEncoder;

import com.example.todoapp.utils.ICheckBCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {



    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new ICheckBCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.cors()
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/v1/login").anonymous()
//                .antMatchers("/api/v1/users/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
//                .and();
//                .addFilterBefore(
//                        new JWTAuthenticationFilter("/api/v1/login", authenticationManager(), service),
//                        UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(
//                        new JWTAuthorizationFilter(),
//                        UsernamePasswordAuthenticationFilter.class);
//    }


    @Override
       protected void configure(HttpSecurity http)throws Exception {
        http.authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(
                Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(
                Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(Collections.singletonList("X-Total-Count"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

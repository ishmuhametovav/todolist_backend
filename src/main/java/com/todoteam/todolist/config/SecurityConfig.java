package com.todoteam.todolist.config;

import com.todoteam.todolist.filter.TokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig
{
    private final TokenFilter tokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(csrf->csrf.disable())
                .sessionManagement(sm->
                                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/api/v1/auth/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin(form->form.disable())
                .httpBasic(basic->basic.disable());

        http.addFilterAfter(tokenFilter, org.springframework.security.web.context.SecurityContextPersistenceFilter.class);


        return http.build();
    }
}

package com.springboot.blog.config;


// Configuration sets the class as java Based Configuration Class

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    SecurityConfig(UserDetailsService userDetailsService) {

        // Constructor Dependency injection
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

        // this authentication manager will automatically get UserDetails using userDetailsService and  password encoder
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests(
                        (authorize) ->
//                        authorize.anyRequest().authenticated())
                                authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                        .anyRequest().authenticated())
                // permit all GET ROUTES to every user
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    //securing REST API with In-memory Authentication

    /*
        what is an in-memory  object ?

        In contrast to persistent storage like databases or disk files,
        in-memory objects are temporary and exist only while the program is running.

     */


    // IN-MEMORY BASED AUTHENTICATED : COMMENTED AS LATER DATABASE BASED AUTHENTICATION IS USED
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails tanveer = User.builder()
//                .username("tanveer")
//                .password(passwordEncoder().encode("tanveer")) // plain  text password wont' work in case of spring security so we need passwrod encodeer
//                .roles("USER")
//                .build();
//
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(tanveer,admin);
//    }


}

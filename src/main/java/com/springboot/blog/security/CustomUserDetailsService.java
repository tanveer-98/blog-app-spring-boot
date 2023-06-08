package com.springboot.blog.security;

import com.springboot.blog.config.SecurityConfig;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {
    // this is used to implement database based authentication method

    private UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        // Optional object has the feature of orElseThrow method
        User user = userRepository.findByUserNameOrEmail(userNameOrEmail , userNameOrEmail)
                .orElseThrow(()-> new UsernameNotFoundException("User Not found in the databaese"));


        // Set<GrantedAuthirty> is a requirement by Spring security
        Set<GrantedAuthority> authorities  = user
                .getRoles()
                .stream() // convert to stream to map to new format
                .map((role)->new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // converting user object to spring required user Object type



        return  new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

    }
}

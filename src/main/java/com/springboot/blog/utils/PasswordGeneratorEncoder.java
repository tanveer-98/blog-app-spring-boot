package com.springboot.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("admin"));
        System.out.println(passwordEncoder.encode("tanveer"));
        // result 1 : $2a$10$jImw/r/.wvDIPD2niEcESeB9NvDjlE.g2j8cXqBb.oz30.2ko47Ea
        //  result 2 : $2a$10$poercdCNp7oZLzjku7VJSeZYzHloJ95tBWkfLz5lfAuHIa.oC4W2q

    }
}

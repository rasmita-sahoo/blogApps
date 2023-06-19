package com.blog.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;

public class MainClass {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();     //Use to hide the password------------------------
        // PasswordDecoder decoder = new BCryptPasswordDecoder();  //Use to Show the password------------------------
        System.out.print(encoder.encode("testing"));
    }
}

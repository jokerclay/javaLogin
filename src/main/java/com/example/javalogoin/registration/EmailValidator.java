package com.example.javalogoin.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class EmailValidator implements Predicate<String> {
    public boolean test(String s) {
        // TODO: Regex to validate email
        return true;
    }
}


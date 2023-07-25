package com.homework.spring.util;

import org.springframework.stereotype.Component;

@Component
public class Util {

    public static boolean isStringEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}

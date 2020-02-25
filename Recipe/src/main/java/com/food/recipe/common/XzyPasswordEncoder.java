package com.food.recipe.common;

import com.food.recipe.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

public class XzyPasswordEncoder implements PasswordEncoder {

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5Util.encode((String)rawPassword));
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.encode((String)rawPassword);
    }
}

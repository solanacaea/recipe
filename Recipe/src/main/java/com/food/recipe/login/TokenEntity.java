package com.food.recipe.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenEntity {
    private String userId;
    private String token;
}

package com.food.recipe.login;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class InMemoryTokenService implements TokenService {

    private final Cache<String, TokenEntity> userTokens = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(7, TimeUnit.DAYS)
            .build();

    @Override
    public TokenEntity createToken(String userId) {
        String token = UUID.randomUUID().toString();
        TokenEntity entity = new TokenEntity(userId, token);
        userTokens.put(userId, entity);
        return entity;
    }

    @Override
    public boolean checkToken(TokenEntity entity) {
        if (entity == null) {
            return false;
        }
        TokenEntity token = userTokens.getIfPresent(entity.getUserId());

        if (token == null || StringUtils.isEmpty(token.getToken())) {
            return false;
        }
        return token.getToken().equals(entity.getToken());
    }

    @Override
    public TokenEntity getToken(String authentication) {
        if (!StringUtils.isEmpty(authentication)) {
            int split = authentication.indexOf("@");
            String userId = authentication.substring(0, split);
            String token = authentication.substring(split);
            return new TokenEntity(userId, token);
        }
        return null;
    }

    @Override
    public void deleteToken(String userId) {
        userTokens.invalidate(userId);
    }
}

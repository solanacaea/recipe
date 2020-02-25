package com.food.recipe.web;

import com.food.recipe.annotation.CurrentUser;
import com.food.recipe.user.RecipeUser;
import com.food.recipe.user.RecipeUserDetailsService;
import com.food.recipe.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RecipeUserDetailsService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果参数类型是UserEntity并且有CurrentUser注解则支持
        return methodParameter.getParameterType().isAssignableFrom(RecipeUser.class) &&
                methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer container,
                                  NativeWebRequest request,
                                  WebDataBinderFactory factory) throws IllegalStateException {
        //取出AuthorizationInterceptor中注入的userId
        String currentUserId = (String) request.getAttribute(
                Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (currentUserId != null) {
            return userService.findByUserId(currentUserId);
        }
        throw new IllegalStateException("用户不存在！");
    }
}

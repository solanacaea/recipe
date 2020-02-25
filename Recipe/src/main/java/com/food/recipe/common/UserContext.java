package com.food.recipe.common;

import org.omg.CORBA.CurrentHelper;

import java.util.HashMap;
import java.util.Map;

public class UserContext {

    private static ThreadLocal<Map<String, String>> currentMap = new ThreadLocal<>();

    public static void setRequestHost(String host) {
        getMapper().put("host", host);
    }
    public static String getRequestHost() {
        return getMapper().get("host");
    }

    private static Map<String, String> getMapper() {
       if (currentMap.get() == null) {
           currentMap.set(new HashMap<>());
       }
       return currentMap.get();
    }
}

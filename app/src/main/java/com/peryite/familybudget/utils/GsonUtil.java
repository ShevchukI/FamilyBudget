package com.peryite.familybudget.utils;

import com.google.gson.Gson;

public class GsonUtil {

    private static final Gson gson = new Gson();

    public static String toJson(Object object){
        return gson.toJson(object);
    }

    public static Object fromJson(String json, Class objClass){
        return gson.fromJson(json, objClass);
    }
}

package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {

    public static void injectObjects(Object target, String fieldName, Object toInject){
        boolean privateValue = false;
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            if(!f.isAccessible()){
                f.setAccessible(true);
                privateValue = true;
            }
            f.set(target, toInject);

            if(privateValue){
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

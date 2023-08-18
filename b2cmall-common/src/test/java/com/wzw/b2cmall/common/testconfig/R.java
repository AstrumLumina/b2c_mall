package com.wzw.b2cmall.common.testconfig;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public  class R<T> {
    private T data;

    public R() {

    }

    public  boolean isInstanceOfGenericType(Object obj) {
        Class<? extends R> aClass = this.getClass();
        for (Field declaredField : aClass.getDeclaredFields()) {
            if (declaredField.getName().equals("data")){
                Class<?> type = declaredField.getType();
                Class<?> declaringClass = declaredField.getDeclaringClass();
                Type genericType = declaredField.getGenericType();
                Class<? extends Type> aClass1 = genericType.getClass();
                System.out.println(type);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        R<String> stringR = new R<String>();
        String string="str";
        stringR.isInstanceOfGenericType(string);
    }
}
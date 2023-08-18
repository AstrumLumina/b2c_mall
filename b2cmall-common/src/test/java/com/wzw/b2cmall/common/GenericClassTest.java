package com.wzw.b2cmall.common;

import com.wzw.b2cmall.common.testconfig.GenericClass;
import com.wzw.b2cmall.common.testconfig.R;

public class GenericClassTest {


    public static void main(String[] args) {
        GenericClass<Integer> intInstance = new GenericClass<>();
        intInstance.staticValue = 10;

        GenericClass<String> stringInstance = new GenericClass<>();
        System.out.println(stringInstance.staticValue); // 输出 10
    }
}

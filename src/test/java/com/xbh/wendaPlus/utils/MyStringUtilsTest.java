package com.xbh.wendaPlus.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStringUtilsTest {

    @Test
    void getExclusiveSymbolStrLength() {
        String s = "...,,,......。。。，，，aa";

        Integer integer = MyStringUtils.getExclusiveSymbolStrLength(s);

        System.out.println(integer);
    }
}
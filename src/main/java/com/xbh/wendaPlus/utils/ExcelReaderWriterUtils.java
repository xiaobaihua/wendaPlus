package com.xbh.wendaPlus.utils;

import java.lang.reflect.Field;

/**
 * excel读写相关类
 * @author Administrator
 */
public class ExcelReaderWriterUtils {
    public static String getFieldName(Field field){
        String name = field.getName();
        String[] strings = name.split("[.]");
        return strings[strings.length - 1];
    }
}

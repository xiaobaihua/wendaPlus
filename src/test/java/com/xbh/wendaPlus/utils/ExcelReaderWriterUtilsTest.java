package com.xbh.wendaPlus.utils;

import com.xbh.wendaPlus.bean.ExcelBean;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ExcelReaderWriterUtilsTest {

    @Test
    void getFieldName() {
        for (Field declaredField : ExcelBean.class.getDeclaredFields()) {
            String fieldName = ExcelReaderWriterUtils.getFieldName(declaredField);
            System.out.println(fieldName);
        }

    }
}
package com.xbh.wendaPlus.cofig;

import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.bean.ExcelBean;
import javafx.util.Pair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ExcelCellIndex {
//    private static Map<String, Integer> cellIndex = new HashMap<>(10);
//    static {
//        for (ExcelFieldConfig value : ExcelFieldConfig.values()) {
//            cellIndex.put(value.toString(), -1);
//        }
//    }
//    public static Integer NO = -1;
//    public static Integer TITLEINDEX = -1;
//    public static Integer SEXINDEX = -1;
//    public static Integer AGEINDEX = -1;
//    public static Integer ISSUEINDEX = -1;
//    public static Integer RESULTBEGININDEX = -1;

    /**
     * @param row 行
     * @return 第一行是标题true 否则false
     */
    public static boolean isHeadLine(Row row) {
        if (row != null) {
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                Object cellValue = null;
                if (cell != null) {
                    if (cell.getCellType() == 0) {
                        cellValue = cell.getNumericCellValue();
                    } else if (cell.getCellType() == 1) {
                        cellValue = cell.getStringCellValue();
                    }

                    // 判断当前行是否为第一行
                    // 是否取值成功
                    if (cellValue != null) {
                        for (String key : ExcelFieldConfig.CYTranslation.keySet()) {
                            MutablePair<String, Integer> stringIntegerPair = ExcelFieldConfig.CYTranslation.get(key);
                            if (stringIntegerPair.getKey().equals(cellValue)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 设置excel中属性与bean的对应关系
     *
     * @param row
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void setBeanIndex(Row row) throws IllegalAccessException, InstantiationException {
        if (row != null) {
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    String cellValue = cell.getStringCellValue();
                    for (String key : ExcelFieldConfig.CYTranslation.keySet()) {
                        MutablePair<String, Integer> stringIntegerPair = ExcelFieldConfig.CYTranslation.get(key);
                        if (stringIntegerPair.getKey().equals(cellValue)) {
                            stringIntegerPair.setValue(i);
                        }
                    }
                }
            }
        }

    }

    public static Object cellToBean(Row row, Class clazz) throws Exception {
        if (clazz == AskBean.class) {
            AskBean bean = ExcelCellIndex.toAskBean(row, clazz);
            return bean;
        }

        return null;
    }

    private static AskBean toAskBean(Row row, Class clazz) throws Exception{
        AskBean bean = (AskBean) clazz.newInstance();
//        for (Field field : ExcelBean.class.getDeclaredFields()) {
        for (int i = 0; i < ExcelFieldConfig.CYTranslation.keySet().size(); i++) {
            Object[] keys = ExcelFieldConfig.CYTranslation.keySet().toArray();
            Object key = keys[i];
            try {
                Field field = AskBean.class.getDeclaredField(key.toString());
                String[] s = field.toString().split("[.]");
                String fieldName = s[s.length - 1];
                if (key.equals(fieldName)) {
                    MutablePair<String, Integer> pair = ExcelFieldConfig.CYTranslation.get(key);
                    if (pair.getValue() > -1) {
                        Cell cell = row.getCell(pair.getValue());
                        if (cell != null) {
                            // 填充值
                            Object value = "";
                            if (cell.getCellType() == 0 && (Double)cell.getNumericCellValue() != null) {
                                Double d = cell.getNumericCellValue();
                                value = d.intValue();
                            } else if (cell.getCellType() == 1 && cell.getStringCellValue() != null) {
                                value = cell.getStringCellValue();
                            }

                            ExcelCellIndex.setFieldValue(field, bean, value.toString());
                        }
                    }

                }

//                MutablePair<String, Integer> pair = ExcelFieldConfig.CYTranslation.get(key);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//                if (field.toString() )
//                System.out.println(field.toGenericString());

//        }

        return bean;
}

    /**
     * 设置字段值
     * @param field 字段
     * @param target 目标对象
     * @param value 值
     */
    private static void setFieldValue(Field field, Object target, String value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(target, value);
        field.setAccessible(false);
    }
}

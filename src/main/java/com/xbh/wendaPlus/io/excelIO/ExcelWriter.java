package com.xbh.wendaPlus.io.excelIO;

import com.sun.xml.internal.bind.v2.TODO;
import com.xbh.wendaPlus.bean.ExcelBean;
import com.xbh.wendaPlus.cofig.ExcelFieldConfig;
import com.xbh.wendaPlus.utils.ExcelReaderWriterUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ExcelWriter implements IExcelWriter {
    private FileOutputStream os = null;
    private Workbook workbook = null;
    public ExcelWriter() {
    }

    public ExcelWriter(File outFile) throws FileNotFoundException {
        this.os = new FileOutputStream(outFile);
    }

    private Sheet createSheet(File file) {
        TODO 创建工作表方法重载;
        return null;
    }

    private Sheet createSheet() {
        if (workbook == null) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            this.workbook = workbook;
            XSSFSheet sheet = workbook.createSheet();
            return sheet;
        } else {
            return this.workbook.getSheetAt(0);
        }
    }

    @Override
    public void write(List resultList, Class clazz) throws NoSuchFieldException, IllegalAccessException, IOException {
        Sheet sheet = createSheet();
        for (int i = 0; i < resultList.size(); i++) {
            Row row = sheet.getRow(i);
            ExcelBean excelBean = (ExcelBean)resultList.get(i);

            if (row == null) {
                row = sheet.createRow(i);
            }
            writeAskBeanToRow(row, excelBean);
        }
        workbook.write(os);


//        for (Field field : clazz.getDeclaredFields()) {
//            if (field != null) {
//                String fieldName = ExcelReaderWriterUtils.getFieldName(field);
//                MutablePair<String, Integer> pair = ExcelFieldConfig.CYTranslation.get(fieldName);
//                if (pair.getValue() > -1) {
//                    if (isResultList(field, clazz)) {
//                        setResultList(Row row, pair.getValue(), field.get(bean))
//                    }
//                }
//                    System.out.println(pair.getKey() + ": "  );
//            }
//        }
    }

    public void writeAskBeanToRow(Row row, ExcelBean bean) throws NoSuchFieldException, IllegalAccessException {
        Class<? extends ExcelBean> beanClass = bean.getClass();
        for (Field field : beanClass.getDeclaredFields()) {
            if (field != null) {
                field.setAccessible(true);

                String fieldName = ExcelReaderWriterUtils.getFieldName(field);
                MutablePair<String, Integer> pair = ExcelFieldConfig.CYTranslation.get(fieldName);
                if (pair != null && pair.getValue() > -1) {
                    if (isResultList(field, beanClass) && field.get(bean) != null) {
                        setResultList(row, pair.getValue(), bean.getResultList());
                    } else {
                        Object value = field.get(bean);
                        if (value != null) {
                            writeToCell(row, pair.getValue(), value.toString());
                        }

                    }
                }

                field.setAccessible(false);
            }
        }
    }

    // 写入row中
    private void writeToCell(Row row, Integer index, String value) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            row.createCell(index).setCellValue(value);
        }
    }

//    private void setExcelCell(Row row, AskBean bean) throws IllegalAccessException {
//        for (Field field : bean.getClass().getDeclaredFields()) {
////            if (field.getName() == bean.getClass().getDeclaredField(""))
//            if (field.get(bean) != null) {
//
//            }
//        }
//    }

    private void setResultList(Row row, Integer index, List<String> values) {
        for (int i = 0; i < values.size(); i++) {
            String value = values.get(i);
            writeToCell(row, index + i, String.valueOf(value));
        }
    }

    private boolean isResultList(Field field, Class clazz) throws NoSuchFieldException {
        Field resultBegin = clazz.getDeclaredField("resultBegin");
        if (field.equals(resultBegin)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setOutputResource(File file) throws FileNotFoundException {
        os = new FileOutputStream(file);
    }

    @Override
    public void close() throws IOException {
        if (os != null) {
            os.close();
        }
    }
}

package com.xbh.wendaPlus.io.excelIO;

import com.sun.org.apache.xpath.internal.objects.XNull;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.cofig.ExcelCellIndex;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * excel读取
 */
public class ExcelReader<T> implements IExcelReader<T>{
    private FileInputStream is = null;
    private int currInt = 0;

    public ExcelReader(FileInputStream is) {
        this.is = is;
    }

    @Override
    public T reader(File url) throws Exception {
        return null;
    }

    @Override
    public List<T> readToList(File file, Class clazz) {
        ArrayList<T> ts = new ArrayList<>();


        return null;
    }

    @Override
    public List<T> readToList(Class clazz) throws Exception {
        ArrayList<T> ts = new ArrayList<>();
        XSSFWorkbook xssfSheets = new XSSFWorkbook(is);
        XSSFSheet sheetAt = xssfSheets.getSheetAt(0);
        for (Row row : sheetAt) {
            if (ExcelCellIndex.isHeadLine(row)) {
                ExcelCellIndex.setBeanIndex(row);
            }
            ts.add((T) ExcelCellIndex.cellToBean(row, clazz));
        }
        // 添加结果标题
        addResultHeadLine(ts, clazz);

        return ts;
    }

    private void addResultHeadLine(List<T> beans, Class clazz) {
        if (clazz == AskBean.class) {
            AskBean t = (AskBean)beans.get(0);
            if (t.getResultBegin() != null) {
                t.getResultList().add("答案1");
                t.getResultList().add("答案2");
                t.getResultList().add("答案3");
            }
        }

    }

    public T reader(Class clazz) throws Exception {
        XSSFWorkbook xssfSheets = new XSSFWorkbook(is);
        XSSFSheet sheetAt = xssfSheets.getSheetAt(0);
        XSSFRow row = sheetAt.getRow(1);
        if (ExcelCellIndex.isHeadLine(row)) {
            ExcelCellIndex.setBeanIndex(row);
        }

        return (T) ExcelCellIndex.cellToBean(row, clazz);
    }

    /**
     * excel row转excel
     * @param row
     * @param clazz bean类型
     * @return
     */
    private T cellToBean(Row row, Class clazz) throws InstantiationException, IllegalAccessException {
        for (int i = 0; i < row.getLastCellNum(); i++) {
            // 判断是否是首行
            if (ExcelCellIndex.isHeadLine(row)) {
                // 如果是首行就保存相关的位置
                ExcelCellIndex.setBeanIndex(row);
            }

            // 封装bean
            Cell cell = row.getCell(i);
            if (cell != null) {

            }
        }

        return null;
    }

    @Override
    public void setInputResource(File file) throws IOException {
        if (is != null) {
            this.close();
        }

        if (file == null) {
            new RuntimeException("文件为空");
        }

        is = new FileInputStream(file);
    }

    @Override
    public void close() throws IOException {
        is.close();
    }
}

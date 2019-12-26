package com.xbh.wendaPlus.io.excelIO;

import com.xbh.wendaPlus.bean.ArticleBean;
import com.xbh.wendaPlus.io.IReader;
import lombok.Cleanup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.regexp.RE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xbh
 * @Date: 2019/12/26  10:52
 * @describe:
 **/
public class ArticleExcelReader<T> implements IReader {
    private InputStream is = null;

    @Override
    public Object reader(File url) throws Exception {
        return null;
    }

    public List<ArticleBean> readExcelToList(File excelFile) throws IOException {
        setInputResource(excelFile);
        List<ArticleBean> articleBeanList;

        articleBeanList = readExcel();

        this.close();
        return articleBeanList;
    }

    private List<ArticleBean> readExcel() throws IOException {
        List<ArticleBean> articleBeanList = new ArrayList<>();
        XSSFWorkbook xssfSheets = new XSSFWorkbook(is);
        XSSFSheet sheetAt = xssfSheets.getSheetAt(0);
        for (Row row : sheetAt) {
            articleBeanList.add(rowToBean(row));
        }

        return articleBeanList;
    }

    private ArticleBean rowToBean(Row row) {
        ArticleBean articleBean = null;
        if (row != null && row.getLastCellNum() >= 2) {
            articleBean = new ArticleBean();

            Cell cell = row.getCell(0);
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                articleBean.setWordsID(Double.toString(cell.getNumericCellValue()));
            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                articleBean.setWordsID(cell.getStringCellValue());
            }

            String title = row.getCell(1).getStringCellValue();
            articleBean.setTitle(title);

            Cell contentCell = row.getCell(2);
            if (contentCell == null) {
                articleBean.setContent("");
            } else if (contentCell.getCellType() == Cell.CELL_TYPE_STRING) {
                articleBean.setContent(contentCell.getStringCellValue());
            } else if (contentCell.getCellType() == Cell.CELL_TYPE_NUMERIC ) {
                articleBean.setContent(Double.toString(contentCell.getNumericCellValue()));
            }
        }

        return articleBean;
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
        this.is.close();
    }
}

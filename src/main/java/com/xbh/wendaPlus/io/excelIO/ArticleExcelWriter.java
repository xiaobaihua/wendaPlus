package com.xbh.wendaPlus.io.excelIO;

import com.xbh.wendaPlus.bean.ArticleBean;
import com.xbh.wendaPlus.cofig.ArticleExcelRowIndexMap;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

/**
 * @Author: xbh
 * @Date: 2019/12/28  21:40
 * @describe:
 **/
public class ArticleExcelWriter {
    private File outFile = null;
    private File inFile = null;
    private OutputStream os = null;
    private InputStream is = null;

    public ArticleExcelWriter(File inFile, File outFile) throws IOException {
        if (outFile != null && inFile != null) {
            this.outFile = outFile;
            this.inFile = inFile;
            os = new FileOutputStream(outFile);
            is = new ByteArrayInputStream(FileUtils.readFileToByteArray(inFile));
        }else {
            new NullPointerException("请检查输出目录");
        }
    }

    public void writerExcle(List<ArticleBean> beans) throws IOException, InvalidFormatException {
//        Workbook workbook = WorkbookFactory.create(is);
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet;
        if (workbook.getNumberOfSheets() <= 0) {
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.getSheetAt(0);
        }

        for (int i = 0; i < beans.size(); i++) {
            ArticleBean articleBean = beans.get(i);
            Row row = null;
            row = sheet.getRow(i);
            if (row == null) {
                Row newROw =  sheet.createRow(i);
                writerRow(articleBean, newROw);
            }
        }
        workbook.write(os);
    }

    private void writerRow(ArticleBean bean, Row row) {
        Cell words = null;
        Cell title = null;
        Cell content = null;

        words = row.getCell(ArticleExcelRowIndexMap.WORDSID);
        title = row.getCell(ArticleExcelRowIndexMap.TITLE);
        content = row.getCell(ArticleExcelRowIndexMap.CONTENT);

        if (words == null) {
            words = row.createCell(ArticleExcelRowIndexMap.WORDSID);
            words.setCellValue(bean.getWordsID());
        }

        if (title == null) {
            title = row.createCell(ArticleExcelRowIndexMap.TITLE);
            title.setCellValue(bean.getTitle());
        }

        if (content == null) {
            content = row.createCell(ArticleExcelRowIndexMap.CONTENT);
            content.setCellValue(bean.getContent());
        }
    }
}

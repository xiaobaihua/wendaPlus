package com.xbh.wendaPlus.cofig;

import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.bean.ExcelBean;
import com.xbh.wendaPlus.io.excelIO.ExcelReader;
import com.xbh.wendaPlus.io.excelIO.ExcelWriter;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


class ExcelCellIndexTest {

    @org.junit.jupiter.api.Test
    void isHeadLine() throws Exception {

        ExcelReader<AskBean> reader = new ExcelReader<>(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\1.xlsx")));
        List<AskBean> askBeans = reader.readToList(AskBean.class);
        ArrayList<ExcelBean> excelBeans = new ArrayList<>();
        reader.close();


        for (int i = 1; i < askBeans.size(); i++) {
            if (i == 1) {
                excelBeans.add(new ExcelBean(askBeans.get(0)));
            }
            AskBean askBean = askBeans.get(i);
            ExcelBean excelBean = new ExcelBean(askBean);
            excelBean.setSex("男");
            excelBean.setAge("123");
//            excelBean.setIssue("你好你好你好啊啊啊啊啊啊啊啊啊啊啊啊");
//            excelBean.setResult("日日日日日日日日日日日日日日一ir一日日");
            for (int j = 0; j < 3; j++) {
                excelBean.getResultList().add(String.valueOf(System.currentTimeMillis()));
            }

            excelBeans.add(excelBean);
        }

        ExcelWriter excelWriter = new ExcelWriter(new File("C:\\Users\\Administrator\\Desktop\\123.xlsx"));
        excelWriter.write(excelBeans, ExcelBean.class);
    }

    @org.junit.jupiter.api.Test
    void setHeadLineBean() {

    }

    @org.junit.jupiter.api.Test
    void cellToBean() {
    }
}
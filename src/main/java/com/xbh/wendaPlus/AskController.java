package com.xbh.wendaPlus;

import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.bean.ExcelBean;
import com.xbh.wendaPlus.io.excelIO.ExcelReader;
import com.xbh.wendaPlus.io.excelIO.ExcelWriter;
import com.xbh.wendaPlus.spider.SpiderController;
import com.xbh.wendaPlus.spider.baidu.ContentSpider;
import com.xbh.wendaPlus.spider.baidu.HtmlUnit.HtmlUnit;
import com.xbh.wendaPlus.url.UrlFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xbh
 * @Date: 2019/12/24  19:42
 * @describe: 问答模式控制器
 **/
public class AskController {
    public static List<AskBean> askBeanList = new ArrayList();
    private File inFile;
    private File outFile;

    public AskController() {

    }

    public AskController(List askBeanList) {
        askBeanList = askBeanList;
    }

    public void initController(File inFile, File outFile) {
        this.setInFile(inFile);
        this.setOutFile(outFile);
    }

    public void execute() throws Exception {
        // 添加第一个百度连接
        boolean b = addOneUrl();
        if (b) {
            HtmlUnit htmlUnit = new HtmlUnit();
            htmlUnit.getOSData(askBeanList);
        }

        // 爬取内容
        ContentSpider contentSpider = new ContentSpider();
        contentSpider.addUrlAndStart(askBeanList);

        // 写入excel
        writerExcel(askBeanList);
    }

    private void writerExcel(List<AskBean> askBeanList) throws IllegalAccessException, NoSuchFieldException, IOException {
        List<ExcelBean> excelBeans = askListBeanTOExcelBeanList(askBeanList);
        ExcelWriter excelWriter = null;
        if (outFile != null) {
            excelWriter = new ExcelWriter(outFile);
            excelWriter.write(excelBeans, ExcelBean.class);
        }
    }

    private List<ExcelBean> askListBeanTOExcelBeanList(List<AskBean> beans)  {
        ArrayList<ExcelBean> excelBeans = new ArrayList<>();

        for (AskBean bean : beans) {
            ExcelBean excelBean = new ExcelBean(bean);
            excelBeans.add(excelBean);
        }

        return excelBeans;
    }

    private boolean addOneUrl() throws Exception {
        UrlFactory urlFactory = new UrlFactory();
        if (this.inFile != null) {
            ExcelReader<AskBean> reader = new ExcelReader<>(new FileInputStream(this.inFile));
            List<AskBean> list = reader.readToList(AskBean.class);
            if (list != null) {
                // 添加到集合中
                askBeanList.addAll(list);
                SpiderController.beanList = askBeanList;
            } else {
                return false;
            }
            for (AskBean bean : askBeanList) {
                ArrayList<String> urlList = new ArrayList<>();
                if (SpiderController.CurrentTargetSite != null) {
                    String title = null;
                    if (bean.getTitle() != null) {
                        title = bean.getTitle();
                    } else if (bean.getTitle1() != null) {
                        title = bean.getTitle1();
                    }
                    String production = urlFactory.production(title, SpiderController.CurrentTargetSite);
                    urlList.add(production);
                }
                bean.setOneUrl(urlList);
            }
        }

        return true;
    }

    public File getOutFile() {
        return outFile;
    }

    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }

    public File getInFile() {
        return inFile;
    }

    public void setInFile(File inFile) {
        this.inFile = inFile;
    }
}

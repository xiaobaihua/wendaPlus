package com.xbh.wendaPlus.spider;

import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.bean.ExcelBean;
import com.xbh.wendaPlus.io.IWriter;
import com.xbh.wendaPlus.io.excelIO.ExcelReader;
import com.xbh.wendaPlus.io.excelIO.ExcelWriter;
import com.xbh.wendaPlus.spider.baidu.BaiDuSpider;
import com.xbh.wendaPlus.spider.baidu.ContentSpider;
import com.xbh.wendaPlus.ui.MainPageController;
import com.xbh.wendaPlus.url.UrlFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬虫控制类
 *
 * @author Administrator
 */
public class SpiderController {
    public static Long completed = 0L;
    private File outFile;
    private File inFile;
    public static List<AskBean> askBeanList = new ArrayList();
    public static String CurrentTargetSite = null;

    public void start(String[] args) {
        MainPageController.launch(MainPageController.class, args);
    }

    // mainPage全部完毕回调函数
    public void spiderRun(MainPageController pageController) throws Exception {
        // 添加第一个百度连接
        boolean b = addOneUrl();
        if (b == true) {
            BaiDuSpider baiDuSpider = new BaiDuSpider();
            baiDuSpider.addUrlAndStart(askBeanList);
        }

        // 添加第二个结果页重定向连接
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

    private List<ExcelBean> askListBeanTOExcelBeanList(List<AskBean> beans) {
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
            } else {
                return false;
            }

            for (AskBean bean : askBeanList) {
                ArrayList<String> urlList = new ArrayList<>();
                if (CurrentTargetSite != null) {
                    String title = null;
                    if (bean.getTitle() != null) {
                        title = bean.getTitle();
                    } else if (bean.getTitle1() != null) {
                        title = bean.getTitle1();
                    }
                    String production = urlFactory.production(title, CurrentTargetSite);
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

    public SpiderController setOutFile(File outFile) {
        this.outFile = outFile;
        return this;
    }

    public File getInFile() {
        return inFile;
    }

    public SpiderController setInFile(File inFile) {
        this.inFile = inFile;
        return this;
    }

}

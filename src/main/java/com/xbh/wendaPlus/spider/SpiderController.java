package com.xbh.wendaPlus.spider;

import com.xbh.wendaPlus.ArticleController;
import com.xbh.wendaPlus.AskController;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.bean.ExcelBean;
import com.xbh.wendaPlus.io.IWriter;
import com.xbh.wendaPlus.io.excelIO.ExcelReader;
import com.xbh.wendaPlus.io.excelIO.ExcelWriter;
import com.xbh.wendaPlus.spider.baidu.BaiDuSpider;
import com.xbh.wendaPlus.spider.baidu.ContentSpider;
import com.xbh.wendaPlus.spider.baidu.HtmlUnit.HtmlUnit;
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
    public static int runType;
    private File outFile;
    private File inFile;
    public static List beanList = new ArrayList();
//    public static String CurrentTargetSite = null;

    public void start(String[] args) {
        MainPageController.launch(MainPageController.class, args);
    }

    // mainPage全部完毕回调函数
    public void spiderRun(MainPageController pageController) throws Exception {
        if (runType == 0) {
            // 问答模式
            AskController askController = new AskController();
            askController.initController(inFile, outFile);
            askController.execute();
        } else if (runType == 1) {
            // 文章模式
            ArticleController controller = new ArticleController();
            controller.initController(inFile, outFile);
            controller.execute();
        }
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

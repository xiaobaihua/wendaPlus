package com.xbh.wendaPlus;

import com.xbh.wendaPlus.bean.ArticleBean;
import com.xbh.wendaPlus.filter.ContentFormat;
import com.xbh.wendaPlus.io.excelIO.ArticleExcelReader;

import com.xbh.wendaPlus.io.excelIO.ArticleExcelWriter;
import com.xbh.wendaPlus.spider.ArticleSpider;
import com.xbh.wendaPlus.url.UrlFactory;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xbh
 * @Date: 2019/12/25  13:37
 * @describe:
 **/
public class ArticleController {
    public static List<ArticleBean> articleBeanList = new ArrayList();
    public static int siteType = 0;
    public static int completed = 0;
    @Getter @Setter private File inFile;
    @Getter @Setter private File outFile;

    public ArticleController() {

    }


    public void initController(File inFile, File outFile) {
        this.setInFile(inFile);
        this.setOutFile(outFile);
    }

    public void execute() throws Exception {
        long millis = System.currentTimeMillis();
        articleBeanList = readExcelFile();
        // 添加第一个百度连接
        boolean b = addOneUrl(articleBeanList);
//        // 开始爬取
//        if (b) {
//            ArticleSpider spider = new ArticleSpider(siteType);
//            spider.spiderRun(articleBeanList);
//        }

        System.out.println((System.currentTimeMillis() - millis) / 1000);
        // 写入前格式化一下
        for (ArticleBean bean : articleBeanList) {
            String content = "";
            if (bean.getContentList().size() > 0) {
                content = ContentFormat.formatContent(bean.getContentList());
            } else {
                content = ContentFormat.formatContent(bean.getContent());
            }

            bean.setContent(content);
        }

        // 写入excel
        writerExcel(ArticleController.articleBeanList);

        System.exit(0);
    }
    private List<ArticleBean> readExcelFile() throws IOException {
        List<ArticleBean> beanList = null;
        if (this.inFile != null) {
            ArticleExcelReader<ArticleBean> excelReader = new ArticleExcelReader<>();
            beanList = excelReader.readExcelToList(this.inFile);
        }

        return beanList;
    }

    private void writerExcel(List<ArticleBean> articleBeanList) throws IllegalAccessException, NoSuchFieldException, IOException, InvalidFormatException {
        ArticleExcelWriter articleExcelWriter = new ArticleExcelWriter(inFile, outFile);
        articleExcelWriter.writerExcle(articleBeanList);
    }

    private boolean addOneUrl(List<ArticleBean> articleBeanList) throws Exception {
        UrlFactory urlFactory = new UrlFactory();
        for (ArticleBean articleBean : articleBeanList) {
            String url = urlFactory.getArticleUrl(articleBean.getTitle(), siteType);
            articleBean.getOneUrlList().add(url);
        }

        return true;
    }
}

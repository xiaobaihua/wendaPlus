package com.xbh.wendaPlus;

import com.xbh.wendaPlus.bean.ArticleBean;
import com.xbh.wendaPlus.io.excelIO.ArticleExcelReader;

import com.xbh.wendaPlus.url.UrlFactory;
import lombok.Getter;
import lombok.Setter;

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
    @Getter @Setter private File inFile;
    @Getter @Setter private File outFile;

    public ArticleController() {

    }


    public void initController(File inFile, File outFile) {
        this.setInFile(inFile);
        this.setOutFile(outFile);

        if (this.getInFile() != null) {

        }
    }

    public void execute() throws Exception {
        articleBeanList = readExcelFile();
        // 添加第一个百度连接
        boolean b = addOneUrl(articleBeanList);


        // 写入excel
        writerExcel(ArticleController.articleBeanList);
    }
    private List<ArticleBean> readExcelFile() throws IOException {
        List<ArticleBean> beanList = null;
        if (this.inFile != null) {
            ArticleExcelReader<ArticleBean> excelReader = new ArticleExcelReader<>();
            beanList = excelReader.readExcelToList(this.inFile);
        }

        return beanList;
    }

    private void writerExcel(List<ArticleBean> articleBeanList) throws IllegalAccessException, NoSuchFieldException, IOException {
//        List<ExcelBean> excelBeans = askListBeanTOExcelBeanList(askBeanList);
//        ExcelWriter excelWriter = null;
//        if (outFile != null) {
//            excelWriter = new ExcelWriter(outFile);
//            excelWriter.write(excelBeans, ExcelBean.class);
//        }
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

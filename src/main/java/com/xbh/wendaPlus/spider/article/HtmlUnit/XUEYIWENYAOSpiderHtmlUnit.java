package com.xbh.wendaPlus.spider.article.HtmlUnit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xbh.wendaPlus.bean.ArticleBean;
import com.xbh.wendaPlus.spider.SpiderController;
import com.xbh.wendaPlus.spider.article.spiderParse.XUNYIWENYAOSpiderParse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: xbh
 * @Date: 2019/12/28  13:51
 * @describe:
 **/
public class XUEYIWENYAOSpiderHtmlUnit {
    ExecutorService executor = null;
    LinkedList<ArticleBean> beans = new LinkedList<>();
    private static int total = 0;
    public static Integer maxThread = 100;

    public XUEYIWENYAOSpiderHtmlUnit() {
        this.executor = Executors.newFixedThreadPool(maxThread);
    }

    private WebClient createWebClient() {
        //新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        //是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setCssEnabled(false);
        //很重要，启用JS
//        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(false);
        //很重要，设置支持AJAX
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        return webClient;
    }

    public void run(String url,  ArticleBean bean) throws InterruptedException {
//        WebClient client = this.getWebClient();
        WebClient client = createWebClient();
        ArrayList<String> strings = new ArrayList<>();
//        BlockingQueue  queue = new LinkedBlockingDeque();
        HtmlPage page = null;
        try {
            page = client.getPage(url);
            //异步JS执行需要耗时,所以这里线程要阻塞15秒,等待异步JS执行结束
            client.waitForBackgroundJavaScript(2000);
            //直接将加载完成的页面转换成xml格式的字符串
            String pageXml = page.asXml();
            //获取html文档
            Document document = Jsoup.parse(pageXml);
            XUNYIWENYAOSpiderParse parse = new XUNYIWENYAOSpiderParse();
            parse.parse(bean, document);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            XUEYIWENYAOSpiderHtmlUnit.total++;
            SpiderController.completed ++;
            System.out.println(total);
            System.out.println("当前线程：  "+ Thread.activeCount());
            client.close();
        }
    }

    public List<ArticleBean> getSearchData(List<ArticleBean> articleBeanList) throws InterruptedException {
        this.beans.addAll(articleBeanList);

        for (ArticleBean bean : articleBeanList) {
            List<String> oneUrl = bean.getOneUrlList();
            if (oneUrl != null && oneUrl.size() > 0) {
                Thread.sleep(100);
                Thread t = new Thread(()->{
                    try {
                        this.run(oneUrl.get(0), bean);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                executor.execute(t);
            }
        }

        while (total != articleBeanList.size()) {
            System.out.println(total != articleBeanList.size());
            System.out.println(total);
            System.out.println(articleBeanList.size());
        }

        return articleBeanList;
    }
}

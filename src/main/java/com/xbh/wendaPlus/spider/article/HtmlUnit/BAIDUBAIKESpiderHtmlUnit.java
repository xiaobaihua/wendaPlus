package com.xbh.wendaPlus.spider.article.HtmlUnit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.xbh.wendaPlus.ArticleController;
import com.xbh.wendaPlus.bean.ArticleBean;
import com.xbh.wendaPlus.spider.SpiderController;
import com.xbh.wendaPlus.spider.article.spiderParse.BAIDUBAIKESpiderParse;
import org.apache.http.cookie.ClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: xbh
 * @Date: 2019/12/28  13:58
 * @describe:
 **/
public class BAIDUBAIKESpiderHtmlUnit {
    ExecutorService executor = null;
    LinkedList<ArticleBean> beans = new LinkedList<>();
    private static int total = 0;
    public static Integer maxThread = 100;

    public BAIDUBAIKESpiderHtmlUnit() {
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
        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.getOptions().setJavaScriptEnabled(false);
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        // 设置cookie
        CookieManager cookieManager = new CookieManager();
        Cookie token = new Cookie(".baidu.com", "___wk_scode_token", "MiWorSR7fvCesGrYBLRqRdvxVelS82A6OxRM7/ArpmQ=");
        cookieManager.addCookie(token);

        return webClient;
    }

    public void run(String url,  ArticleBean bean) throws InterruptedException {
        WebClient client = createWebClient();
        try {
            // 获取文章链接
            List<String> articleUrls = getArticleUrls(client, url);
            bean.getTwoUrlList().addAll(articleUrls);

            // 获取文章内容
             List<String> contentList = getArticleContentList(client, bean.getTwoUrlList());
            bean.getContentList().addAll(contentList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            total++;
            ArticleController.completed ++;
            System.out.println(total);
            System.out.println("当前线程：  "+ Thread.activeCount());
            client.close();
        }
    }

    private List<String> getArticleUrls(WebClient client, String targetUrl) throws IOException {
        HtmlPage page = client.getPage(targetUrl);
        //异步JS执行需要耗时,所以这里线程要阻塞15秒,等待异步JS执行结束
        client.waitForBackgroundJavaScript(2000);
        //直接将加载完成的页面转换成xml格式的字符串
        String pageXml = page.asXml();
        //获取html文档
        Document document = Jsoup.parse(pageXml);
        BAIDUBAIKESpiderParse parse = new BAIDUBAIKESpiderParse();
        ArrayList<String> articleUrlList = parse.parse(document);

        return articleUrlList;
    }

    private List<String> getArticleContentList(WebClient client, List<String> targetUrl) throws IOException {
        ArrayList<String> articleContent = new ArrayList<>();
        HtmlPage page = client.getPage(targetUrl.get(0));
        //异步JS执行需要耗时,所以这里线程要阻塞15秒,等待异步JS执行结束
        client.waitForBackgroundJavaScript(3000);
        //直接将加载完成的页面转换成xml格式的字符串
        String pageXml = page.asXml();
        //获取html文档
        Document document = Jsoup.parse(pageXml);
        BAIDUBAIKESpiderParse parse = new BAIDUBAIKESpiderParse();
        String content = parse.parseContent(document);

        articleContent.add(content);

        return articleContent;
    }

    public List<ArticleBean> getSearchData(List<ArticleBean> articleBeanList) throws InterruptedException {
        this.beans.addAll(articleBeanList);

        for (ArticleBean bean : articleBeanList) {
            List<String> oneUrl = bean.getOneUrlList();
            if (oneUrl != null && oneUrl.size() > 0) {
                Thread.sleep(500);
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

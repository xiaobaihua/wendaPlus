package com.xbh.wendaPlus.spider.youlai;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.spider.SpiderController;
import com.xbh.wendaPlus.spider.baidu.HtmlUnit.HtmlUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: xbh
 * @Date: 2020/1/11  20:09
 * @describe:
 **/
public class YLHtmlUnit {
    ExecutorService executor = null;
    LinkedList<AskBean> beans = new LinkedList<>();
    private static int total = 0;
    public YLHtmlUnit() throws InterruptedException {
        this.executor = Executors.newFixedThreadPool(HtmlUnit.maxThread);
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

    private void run(String url,  AskBean bean) throws InterruptedException {
        WebClient client = createWebClient();
        ArrayList<String> strings = new ArrayList<>();
        HtmlPage page = null;
        try {
            page = client.getPage(url);
            //异步JS执行需要耗时,所以这里线程要阻塞15秒,等待异步JS执行结束
            client.waitForBackgroundJavaScript(2000);
            //直接将加载完成的页面转换成xml格式的字符串
            String pageXml = page.asXml();
            //TODO 下面的代码就是对字符串的操作了,常规的爬虫操作,用到了比较好用的Jsoup库
            //获取html文档
            Document document = Jsoup.parse(pageXml);

            YLPageResultRule rule = new YLPageResultRule();
            rule.parse(document, bean, 60, 90);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            total++;
            SpiderController.completed ++;
            System.out.println(total);
            System.out.println("当前线程：  "+ Thread.activeCount());
            bean.getTwoUrl().addAll(strings);
            client.close();
        }
    }

    public List<AskBean> getOSData(List<AskBean> askBeanList) throws InterruptedException {
        this.beans.addAll(askBeanList);
        int currIndex = 0;
        int i = 0;
        for (AskBean askBean : askBeanList) {
            List<String> twoUrl = askBean.getTwoUrl();
            if (twoUrl != null && twoUrl.size() > 0) {
                Thread t = new Thread(()->{
                    try {
                        this.run(twoUrl.get(0), askBean);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                executor.execute(t);
            }
        }

        while (total != askBeanList.size()) {
            System.out.println(total != askBeanList.size());
            System.out.println(total);
            System.out.println(askBeanList.size());
            if (total > 1) {
                currIndex = total;
                Thread.sleep(3000);
                if (total - currIndex <= 0) {
                    i++;
                    System.out.println(i);
                }
                if (i > 20) {
                    return askBeanList;
                }
            }
        }
        return askBeanList;
    }
}
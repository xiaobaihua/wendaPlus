package com.xbh.wendaPlus.spider.baidu.HtmlUnit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.spider.SpiderController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

/**
 * @Author: xbh
 * @Date: 2019/12/18  17:06
 * @describe:
 **/
public class HtmlUnit {
    private BlockingQueue  queue = new LinkedBlockingDeque();


    int total = 0;

    public HtmlUnit() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            WebClient client = createWebClient();
            try {
                this.queue.put(client);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        return webClient;
    }

    private List<String> run(String url,  AskBean bean) throws InterruptedException {
        WebClient client = this.getWebClient();

        ArrayList<String> strings = new ArrayList<>();
        HtmlPage page = null;
        try {
            page = client.getPage(url);
            //异步JS执行需要耗时,所以这里线程要阻塞15秒,等待异步JS执行结束
            client.waitForBackgroundJavaScript(15000);
            //直接将加载完成的页面转换成xml格式的字符串
            String pageXml = page.asXml();
            //TODO 下面的代码就是对字符串的操作了,常规的爬虫操作,用到了比较好用的Jsoup库
            //获取html文档
            Document document = Jsoup.parse(pageXml);
            //获取元素节点等
            Elements selects = document.select(".result");

            if (selects != null && selects.size() > 0) {
                for (Element element : selects) {
                    Elements aTags = element.select(".t a");
                    if (aTags != null && aTags.size() > 0) {
                        String href = aTags.get(0).attr("href");
                        strings.add(href);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.addWebClient(client);
            total++;
            SpiderController.completed ++;
            System.out.println(total);
        }

        bean.getTwoUrl().addAll(strings);
        return strings;
    }

    public List<AskBean> getOSData(List<AskBean> askBeanList) {
        for (AskBean askBean : askBeanList) {
            while (this.queue.size() <= 0) {

            }

            List<String> oneUrl = askBean.getOneUrl();
            if (oneUrl != null && oneUrl.size() > 0) {
                new Thread(() -> {
                    try {
                        this.run(oneUrl.get(0),  askBean);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }

        while (true) {
            if (askBeanList.size() == this.total ) {
                System.out.println(askBeanList.size());
                System.out.println(this.total);
                System.out.println(this.total == askBeanList.size());
                return askBeanList;
            }
        }
    }

    private WebClient getWebClient() {
        WebClient client = (WebClient) this.queue.poll();
        while (client == null) {
            client = (WebClient) this.queue.poll();
        }

        return client;
    }

    private void addWebClient(WebClient client) throws InterruptedException {
        this.queue.put(client);
    }
}

package com.xbh.wendaPlus.spider.baidu;

import cn.edu.hfut.dmic.webcollector.conf.Configuration;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;
import com.xbh.wendaPlus.AskController;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.spider.ISpider;
import com.xbh.wendaPlus.spider.SpiderController;
import okhttp3.Request;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class BaiDuSpider extends RamCrawler implements ISpider {

    public static class MyRequester extends OkHttpRequester {

//        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0";
//        String cookie = "BAIDUID=A2976BD2E005965910F5A773EF5F4682:FG=1; BIDUPSID=A2976BD2E005965910F5A773EF5F4682; PSTM=1566356188; BD_UPN=13314752";
        String cookie = "BAIDUID=A2976BD2E005965910F5A773EF5F4682:FG=1; BIDUPSID=A2976BD2E005965910F5A773EF5F4682; PSTM=1566356188; BD_UPN=13314752; BDORZ=FFFB88E999055A3F8A630C64834BD6D0; H_PS_PSSID=1462_21091; H_PS_645EC=c7afksAq87YDkU7cisyooUKf4XD58KVbtMQ7UU%2B5C%2FkUir6Gc9hse1%2FGf8aMYrBW6A8t; delPer=0; BD_CK_SAM=1; PSINO=3; BDSVRTM=0";

        // 每次发送请求前都会执行这个方法来构建请求
        @Override
        public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
            // 这里使用的是OkHttp中的Request.Builder
            // 可以参考OkHttp的文档来修改请求头
//            return super.createRequestBuilder(crawlDatum)
//                    .addHeader("User-Agent", userAgent)
//                    .addHeader("Cookie", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
//                    .addHeader("Accept-Encoding", "gzip, deflate, br")
//                    .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
//                    .addHeader("Cache-Control", "max-age=0")
//                    .addHeader("Connection", "keep-alive")
//                    .addHeader("Cookie", "BAIDUID=A2976BD2E005965910F5A773EF5F4682:FG=1; BIDUPSID=A2976BD2E005965910F5A773EF5F4682; PSTM=1566356188; BD_UPN=13314752; BDORZ=FFFB88E999055A3F8A630C64834BD6D0; H_PS_PSSID=1462_21091; H_PS_645EC=c7afksAq87YDkU7cisyooUKf4XD58KVbtMQ7UU%2B5C%2FkUir6Gc9hse1%2FGf8aMYrBW6A8t; delPer=0; BD_CK_SAM=1; PSINO=3; BDSVRTM=0")
//                    .addHeader("Host", "www.baidu.com")
//                    .addHeader("Upgrade-Insecure-Requests", "1");
            return super.createRequestBuilder(crawlDatum)
                    .addHeader("User-Agent", userAgent)
                    .addHeader("Cookie", "QiHooGUID=47FF006FE8BDC2CA3C27E206DB907DBA.1576599006910; _S=nudm7qc6p3jg6jtrkp1kh45a66; opqopq=c99def61eab5c19c801fa66a7f899430.1576599006; __guid=15484592.486576919899187650.1576599020253.0596; count=2; dpr=1; screenw=1; webp=1; __huid=11jE5d9U0PnRYbYpcReIdvEjh3NHq2E0RUfraczLESttc%3D; gtHuid=1");
        }

    }

    public static class soRequester extends OkHttpRequester {
//         每次发送请求前都会执行这个方法来构建请求
//        @Override
//        public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
//            // 这里使用的是OkHttp中的Request.Builder
//            // 可以参考OkHttp的文档来修改请求头
////            return super.createRequestBuilder(crawlDatum)
////                    .addHeader("User-Agent", userAgent)
////                    .addHeader("Cookie", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
////                    .addHeader("Accept-Encoding", "gzip, deflate, br")
////                    .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
////                    .addHeader("Cache-Control", "max-age=0")
////                    .addHeader("Connection", "keep-alive")
////                    .addHeader("Cookie", "BAIDUID=A2976BD2E005965910F5A773EF5F4682:FG=1; BIDUPSID=A2976BD2E005965910F5A773EF5F4682; PSTM=1566356188; BD_UPN=13314752; BDORZ=FFFB88E999055A3F8A630C64834BD6D0; H_PS_PSSID=1462_21091; H_PS_645EC=c7afksAq87YDkU7cisyooUKf4XD58KVbtMQ7UU%2B5C%2FkUir6Gc9hse1%2FGf8aMYrBW6A8t; delPer=0; BD_CK_SAM=1; PSINO=3; BDSVRTM=0")
////                    .addHeader("Host", "www.baidu.com")
////                    .addHeader("Upgrade-Insecure-Requests", "1");
//            return super.createRequestBuilder(crawlDatum);
//        }

    }

    public BaiDuSpider() {
        setResumable(false);
        setThreads(100);
        // 设置请求插件
        setRequester(new soRequester());
        this.conf.setExecuteInterval(2000);
    }

    @MatchType(types = "ksBaidu")
    public void visitOne(Page page, CrawlDatums next) {
        if (page.code() == 301 || page.code() == 302) {
            next.addAndReturn(page.location()).meta(page.meta()).type("ksBaidu");
        } else {
            System.out.println(page);

            final Integer id = Integer.valueOf(page.meta("id"));
            final AskBean bean = AskController.askBeanList.get(id);
            SpiderController.completed++;
            Elements s = page.select("#datalist");

            for (Element element : s.select("span.c_url")) {
                bean.getTwoUrl().add(element.text());
            }
        }
    }
    /**
     * 39页面
     * @param page
     * @param next
     */
    @MatchType(types = "39Baidu")
    public void Visit39(Page page, CrawlDatums next) {
        final Integer id = Integer.valueOf(page.meta("id"));
        final AskBean bean = AskController.askBeanList.get(id);
        SpiderController.completed++;
        Elements s = page.select(".result_l");
        for (Element element : s.select("#datalist")) {
            bean.getTwoUrl().add(element.attr("href"));
        }
    }

    @MatchType(types = "DZYSBaidu")
    public void VisitDZYS(Page page, CrawlDatums next) {
        final Integer id = Integer.valueOf(page.meta("id"));
        final AskBean bean = AskController.askBeanList.get(id);

        SpiderController.completed++;
        Elements s = page.select("h3.res-title");

        for (Element element : s.select("a")) {
            bean.getTwoUrl().add(element.attr("href"));
        }
    }

    @MatchType(types = "BDZDBaidu")
    public void VisitBDZD(Page page, CrawlDatums next) {
        final Integer id = Integer.valueOf(page.meta("id"));
        final AskBean bean = AskController.askBeanList.get(id);

        SpiderController.completed++;
        Elements s = page.select("div.result");
        for (Element element : s.select("a.c-showurl")) {
            bean.getTwoUrl().add(element.attr("href"));
        }
    }

    @MatchType(types = "YLBaidu")
    public void VisitYL(Page page, CrawlDatums next) {
        final Integer id = Integer.valueOf(page.meta("id"));
        final AskBean bean = AskController.askBeanList.get(id);

        SpiderController.completed++;
        Elements s = page.select("h3.res-title");

        for (Element element : s.select("a")) {
            bean.getTwoUrl().add(element.attr("href"));
        }
    }

    @MatchType(types = "myzxBaidu")
    public void Visitmyzx(Page page, CrawlDatums next) {
        final Integer id = Integer.valueOf(page.meta("id"));
        final AskBean bean = AskController.askBeanList.get(id);

        SpiderController.completed++;
        Elements s = page.select("h3.res-title");

        for (Element element : s.select("a")) {
            bean.getTwoUrl().add(element.attr("href"));
        }
    }

    @Override
    public void visit(Page page, CrawlDatums next) {

    }

    @Override
    public void addUrlAndStart(List<AskBean> beans) throws Exception {
        addUrl(beans);
        start();
    }

    @Override
    public void addUrl(List<AskBean> beans) {
        if (AskController.CurrentTargetSite.equals("有问必答_快速问医生")) {
            addUrlAndType(beans, "ksBaidu");
        } else if (AskController.CurrentTargetSite.equals("39健康问答_39健康网_39问医生")) {
            addUrlAndType(beans, "39Baidu");
        } else if (AskController.CurrentTargetSite.equals("大众养生网")) {
            addUrlAndType(beans, "DZYSBaidu");
        } else if (AskController.CurrentTargetSite.equals("百度知道")) {
            addUrlAndType(beans, "BDZDBaidu");
        } else if (AskController.CurrentTargetSite.equals("有来医生")) {
            addUrlAndType(beans, "YLBaidu");
        } else if (AskController.CurrentTargetSite.equals("名医在线网")) {
            addUrlAndType(beans, "myzxBaidu");
        }
    }

    private void addUrlAndType(List<AskBean> beans, String type) {
        if (beans != null) {
            for (int i = 0; i < beans.size(); i++) {
                AskBean askBean = beans.get(i);
                List<String> urls = askBean.getOneUrl();
                for (String url : urls) {
                    CrawlDatum crawlDatum = this.addSeedAndReturn(url);
                    crawlDatum.meta("id", String.valueOf(i));
                    crawlDatum.type(type);
                }
            }
        }
    }
}

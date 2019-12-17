package com.xbh.wendaPlus.spider.baidu;

import cn.edu.hfut.dmic.webcollector.conf.Configuration;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;
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
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.79 Safari/537.36";
//        String cookie = "BAIDUID=A2976BD2E005965910F5A773EF5F4682:FG=1; BIDUPSID=A2976BD2E005965910F5A773EF5F4682; PSTM=1566356188; BD_UPN=13314752";
        String cookie = "BAIDUID=A2976BD2E005965910F5A773EF5F4682:FG=1; BIDUPSID=A2976BD2E005965910F5A773EF5F4682; PSTM=1566356188; BD_UPN=13314752; H_PS_PSSID=1462_21091_30211; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_645EC=d75cbO%2F6iisVhUo6xNJHriC90%2FlEvMCM6Juqu8TxKzVLwTxmdR3cfwb11C4; delPer=0; BD_CK_SAM=1; PSINO=3; BDRCVFR[gltLrB7qNCt]=mk3SLVN4HKm; ZD_ENTRY=baidu";

        // 每次发送请求前都会执行这个方法来构建请求
        @Override
        public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
            // 这里使用的是OkHttp中的Request.Builder
            // 可以参考OkHttp的文档来修改请求头
            return super.createRequestBuilder(crawlDatum)
                    .addHeader("User-Agent", userAgent)
                    .addHeader("Cookie", cookie);
        }

    }

    public BaiDuSpider() {
        setResumable(false);
        setThreads(100);
        // 设置请求插件
        setRequester(new MyRequester());
        this.conf.setExecuteInterval(1000);
    }

    @MatchType(types = "ksBaidu")
    public void visitOne(Page page, CrawlDatums next) {
        if (page.code() != 200){
            System.out.println(page.location());
            System.out.println(page.url());

            System.out.println(123);
        } else {
            final Integer id = Integer.valueOf(page.meta("id"));
            final AskBean bean = SpiderController.askBeanList.get(id);
            SpiderController.completed++;
            Elements s = page.select("div.result");
            for (Element element : s.select("a.c-showurl")) {
                bean.getTwoUrl().add(element.attr("href"));
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
        final AskBean bean = SpiderController.askBeanList.get(id);

        SpiderController.completed++;
        Elements s = page.select("div.result");
        for (Element element : s.select("a.c-showurl")) {
            bean.getTwoUrl().add(element.attr("href"));
        }
    }

    @MatchType(types = "DZYSBaidu")
    public void VisitDZYS(Page page, CrawlDatums next) {
        final Integer id = Integer.valueOf(page.meta("id"));
        final AskBean bean = SpiderController.askBeanList.get(id);

        SpiderController.completed++;
        Elements s = page.select("div.result");
        for (Element element : s.select("a.c-showurl")) {
            bean.getTwoUrl().add(element.attr("href"));
        }
    }

    @MatchType(types = "BDZDBaidu")
    public void VisitBDZD(Page page, CrawlDatums next) {
        final Integer id = Integer.valueOf(page.meta("id"));
        final AskBean bean = SpiderController.askBeanList.get(id);

        SpiderController.completed++;
        Elements s = page.select("div.result");
        for (Element element : s.select("a.c-showurl")) {
            bean.getTwoUrl().add(element.attr("href"));
        }
    }

    @MatchType(types = "YLBaidu")
    public void VisitYL(Page page, CrawlDatums next) {
        final Integer id = Integer.valueOf(page.meta("id"));
        final AskBean bean = SpiderController.askBeanList.get(id);

        SpiderController.completed++;
        Elements s = page.select("div.result");
        for (Element element : s.select("a.c-showurl")) {
            bean.getTwoUrl().add(element.attr("href"));
        }
    }

    @MatchType(types = "myzxBaidu")
    public void Visitmyzx(Page page, CrawlDatums next) {
        final Integer id = Integer.valueOf(page.meta("id"));
        final AskBean bean = SpiderController.askBeanList.get(id);

        SpiderController.completed++;
        Elements s = page.select("div.result");
        for (Element element : s.select("a.c-showurl")) {
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
        if (SpiderController.CurrentTargetSite.equals("有问必答_快速问医生")) {
            addUrlAndType(beans, "ksBaidu");
        } else if (SpiderController.CurrentTargetSite.equals("39健康问答_39健康网_39问医生")) {
            addUrlAndType(beans, "39Baidu");
        } else if (SpiderController.CurrentTargetSite.equals("大众养生网")) {
            addUrlAndType(beans, "DZYSBaidu");
        } else if (SpiderController.CurrentTargetSite.equals("百度知道")) {
            addUrlAndType(beans, "BDZDBaidu");
        } else if (SpiderController.CurrentTargetSite.equals("有来医生")) {
            addUrlAndType(beans, "YLBaidu");
        } else if (SpiderController.CurrentTargetSite.equals("名医在线网")) {
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

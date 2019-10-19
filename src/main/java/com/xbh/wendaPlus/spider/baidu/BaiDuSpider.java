package com.xbh.wendaPlus.spider.baidu;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.spider.ISpider;
import com.xbh.wendaPlus.spider.SpiderController;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class BaiDuSpider extends RamCrawler implements ISpider {
    public BaiDuSpider() {
        setResumable(false);
        setThreads(100);
        conf.setExecuteInterval(1000);
    }

    @MatchType(types = "ksBaidu")
    public void visitOne(Page page, CrawlDatums next) {
        final Integer id = Integer.valueOf(page.meta("id"));
        final AskBean bean = SpiderController.askBeanList.get(id);

        SpiderController.completed++;
        Elements s = page.select("div.result");
        for (Element element : s.select("a.c-showurl")) {
            bean.getTwoUrl().add(element.attr("href"));
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

package com.xbh.wendaPlus.spider.dazhong;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.spider.ISpider;
import com.xbh.wendaPlus.spider.SpiderController;
import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

public class DZSpider extends RamCrawler implements ISpider {
    public DZSpider() {
        setResumable(false);
        setThreads(50);
        conf.setExecuteInterval(1000);
        getConf().setConnectTimeout(10000);
    }

    @MatchType(types = "DZYSQA")
    public void DZVisit(Page page, CrawlDatums next) {
        AskBean askBean = SpiderController.askBeanList.get(Integer.valueOf(page.meta("id")));
        DZPageResultRule rule = new DZPageResultRule();

        askBean.getPages().add(page);
        if (Pattern.matches(".*cndzys.com/shenghuoyangsheng/.*", page.url())) {
//            System.out.println(1);
            rule.parseShengHuoYangSheng(page, askBean, 1, 500);
        } else if (Pattern.matches(".*cndzys.com/xiaobian/.*", page.url())) {
//            System.out.println(2);
            rule.parseXiaoBian(page, askBean, 1, 600);
        } else if (Pattern.matches(".*cndzys.com/renqun/.*", page.url()) || Pattern.matches(".*devm.cndzys.com/zhongyi.*", page.url())) {
//            System.out.println(3);
            rule.parseRenQun(page, askBean, 1, 600);
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
        addUrlAndType(beans, "DZYSQA");
    }

    private void addUrlAndType(List<AskBean> beans, String type) {
        if (beans != null) {
            for (int i = 0; i < beans.size(); i++) {
                AskBean askBean = beans.get(i);
                List<String> urls = askBean.getThreeUrl();
                for (String url : urls) {
                    CrawlDatum crawlDatum = this.addSeedAndReturn(url);
                    crawlDatum.meta("id", String.valueOf(i));
                    crawlDatum.type(type);
                }
            }
        }
    }
}

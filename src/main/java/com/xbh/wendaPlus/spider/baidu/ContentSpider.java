package com.xbh.wendaPlus.spider.baidu;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;
import com.xbh.wendaPlus.AskController;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.spider.ISpider;
import com.xbh.wendaPlus.spider.SpiderController;
import com.xbh.wendaPlus.spider.dazhong.DZPageResultRule;
import com.xbh.wendaPlus.spider.kuaisu.KSPageResultRule;
import com.xbh.wendaPlus.spider.myzx.MYZXRule;
import com.xbh.wendaPlus.spider.threenien.SJPageResultRule;
import com.xbh.wendaPlus.spider.youlai.YLHtmlUnit;
import com.xbh.wendaPlus.spider.youlai.YLPageResultRule;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ContentSpider extends RamCrawler implements ISpider {
    List l = new ArrayList();

    public ContentSpider() {
        setResumable(false);
        setThreads(100);
        conf.setExecuteInterval(1000);
        getConf().setConnectTimeout(10000);
    }

    @MatchType(types = "ksQA")
    public void ksQA(Page page, CrawlDatums next) {
        SpiderController.completed ++;
        // 如果301 , 302就复制meta数据并且添加到下一个任务
        if (page.code() == 301 || page.code() == 302) {
            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
            final String s = page.location();
            if (Pattern.matches(".*120ask.com/question/[0-9].*", s)) {
                if (askBean.getThreeUrl().size() < 10) {
                    next.addAndReturn(page.location()).meta(page.meta()).type("ksQA");
                    askBean.getThreeUrl().add(page.location());
                }
            }
        } else {
            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
            KSPageResultRule rule = new KSPageResultRule();
            rule.parse(page, askBean, 60, 90);
        }
    }

    @MatchType(types = "39QA")
    public void QA39(Page page, CrawlDatums next) {
        SpiderController.completed ++;
        // 如果301 , 302就复制meta数据并且添加到下一个任务
        if (page.code() == 301 || page.code() == 302) {
            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
            final String s = page.location();
            if (Pattern.matches(".*ask.39.net/question/[0-9].*", s)) {
                if (askBean.getThreeUrl().size() < 10) {
                    next.addAndReturn(page.location()).meta(page.meta()).type("39QA");
                    askBean.getThreeUrl().add(page.location());
                }
            }
        } else {
            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
            SJPageResultRule rule = new SJPageResultRule();
            rule.parse(page, askBean, 60, 90);
        }
    }

    @MatchType(types = "DZYSQA")
    public void DZYSQA(Page page, CrawlDatums next) {
        SpiderController.completed ++;
        // 如果301 , 302就复制meta数据并且添加到下一个任务
        if (page.code() == 301 || page.code() == 302) {
            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
            final String s = page.location();
            if (Pattern.matches(".*cndzys.com.*", s)) {
                if (askBean.getThreeUrl().size() < 10) {
                    next.addAndReturn(page.location()).meta(page.meta()).type("DZYSQA");
                    askBean.getThreeUrl().add(page.location());
                }
            }
        } else {
            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
            DZPageResultRule rule = new DZPageResultRule();
            if (Pattern.matches(".*cndzys.com/shenghuoyangsheng/.*", page.url())) {
                rule.parseShengHuoYangSheng(page, askBean, 1, 500);
            } else if (Pattern.matches(".*cndzys.com/xiaobian/.*", page.url())) {
                rule.parseXiaoBian(page, askBean, 1, 600);
            } else if (Pattern.matches(".*cndzys.com/renqun/.*", page.url()) || Pattern.matches(".*devm.cndzys.com/zhongyi.*", page.url())) {
                rule.parseRenQun(page, askBean, 1, 600);
            }
        }
    }

    @MatchType(types = "BDZDQA")
    public void BDZDQA(Page page, CrawlDatums next) {
        SpiderController.completed ++;
        // 如果301 , 302就复制meta数据并且添加到下一个任务
        if (page.code() == 301 || page.code() == 302) {
            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
            final String s = page.location();
            if (Pattern.matches(".*zhidao.baidu.com/question/.*", s)) {
                if (askBean.getThreeUrl().size() < 10) {
                    next.addAndReturn(page.location()).meta(page.meta()).type("BDZDQA");
                    askBean.getThreeUrl().add(page.location());
                }
            }
        } else {
            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
            ZDPageResultRule rule = new ZDPageResultRule();
            rule.parse(page, askBean, 60, 90);
        }
    }

    @MatchType(types = "YLQA")
    public void YLQA(Page page, CrawlDatums next) {
        SpiderController.completed ++;
        // 如果301 , 302就复制meta数据并且添加到下一个任务
        if (page.code() == 301 || page.code() == 302) {
            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
            final String s = page.location();
            if (Pattern.matches(".*youlai.cn/ask/[0-9].*", s)) {
                if (askBean.getThreeUrl().size() < 3) {
//                    next.addAndReturn(page.location()).meta(page.meta()).type("YLQA");
                    askBean.getThreeUrl().add(page.location());
                }
            }
//            if (Pattern.matches(".*zhidao.baidu.com/question/.*", s)) {
//                if (askBean.getThreeUrl().size() < 10) {
//                    next.addAndReturn(page.location()).meta(page.meta()).type("BDZDQA");
//                    askBean.getThreeUrl().add(page.location());
//                }
//            }
        }
//        else {
//            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
//            YLPageResultRule rule = new YLPageResultRule();
//            rule.parse(page, askBean, 60, 90);
//        }
    }

    @MatchType(types = "MYZXQA")
    public void MYZXQA(Page page, CrawlDatums next) {
        SpiderController.completed ++;
        // 如果301 , 302就复制meta数据并且添加到下一个任务
        if (page.code() == 301 || page.code() == 302) {
            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
            final String s = page.location();
//            if (Pattern.matches(".*youlai.cn/ask/[0-9].*", s)) {
            if (Pattern.matches(".*myzx.cn.*", s)) {
                if (askBean.getThreeUrl().size() < 10) {
                    next.addAndReturn(page.location()).meta(page.meta()).type("MYZXQA");
                    askBean.getThreeUrl().add(page.location());
                }
            }
        } else {
            AskBean askBean = AskController.askBeanList.get(Integer.valueOf(page.meta("id")));
            MYZXRule rule = new MYZXRule();
            if (Pattern.matches(".*myzx.cn/mip/video/id/.*", page.url())) {
                rule.parse(page, askBean, 60, 90);
            } else if (Pattern.matches(".*myzx.cn/web/video/newinfo/.*", page.url())) {
                rule.parseVideoPage(page, askBean, 60, 90);
            }

        }
    }



    @Override
    public void visit(Page page, CrawlDatums next) {

    }


    @Override
    public void addUrlAndStart(List<AskBean> beans) throws Exception {
        addUrl(beans);
        start();

        // 有来单独获取内容
        if (AskController.CurrentTargetSite.equals("有来医生")) {
            YLHtmlUnit ylHtmlUnit = new YLHtmlUnit();
            List<AskBean> beanList = ylHtmlUnit.getOSData(beans);
            beans = beanList;
        }
    }

    @Override
    public void addUrl(List<AskBean> beans) {
        if (AskController.CurrentTargetSite.equals("有问必答_快速问医生")) {
            addUrlAndType(beans, "ksQA");
        } else if (AskController.CurrentTargetSite.equals("39健康问答_39健康网_39问医生")) {
            addUrlAndType(beans, "39QA");
        } else if (AskController.CurrentTargetSite.equals("大众养生网")) {
            addUrlAndType(beans, "DZYSQA");
        } else if (AskController.CurrentTargetSite.equals("百度知道")) {
            addUrlAndType(beans, "BDZDQA");
        } else if (AskController.CurrentTargetSite.equals("有来医生")) {
            addUrlAndType(beans, "YLQA");
        }else if (AskController.CurrentTargetSite.equals("名医在线网")) {
            addUrlAndType(beans, "MYZXQA");
        }
    }

    private void addUrlAndType(List<AskBean> beans, String type) {
        if (beans != null) {
            for (int i = 0; i < beans.size(); i++) {
                AskBean askBean = beans.get(i);
                List<String> urls = askBean.getTwoUrl();
                for (String url : urls) {
                    CrawlDatum crawlDatum = this.addSeedAndReturn(url);
                    crawlDatum.meta("id", String.valueOf(i));
                    crawlDatum.type(type);
                }
            }
        }
    }

}

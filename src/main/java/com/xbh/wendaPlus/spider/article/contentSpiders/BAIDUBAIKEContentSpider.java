package com.xbh.wendaPlus.spider.article.contentSpiders;

import cn.edu.hfut.dmic.webcollector.conf.Configuration;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xbh
 * @Date: 2019/12/28  15:34
 * @describe:
 **/
public class BAIDUBAIKEContentSpider extends RamCrawler {
    private ArrayList<String> contentTextList = new ArrayList<>();

    public BAIDUBAIKEContentSpider() {
        Configuration conf = getConf();
        conf.setExecuteInterval(500);
        setThreads(5);
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        if (page.code() == 301 || page.code() == 302) {
            crawlDatums.add(page.location());
        } else {
            System.out.println(123);
        }
    }

    public List<String> myStart() throws Exception {
        super.start();

        return this.contentTextList;
    }
}

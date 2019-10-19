package com.xbh.wendaPlus.spider;

import com.xbh.wendaPlus.bean.AskBean;

import java.util.List;

/**
 * 爬虫控制器
 * @author Administrator
 */
public interface ISpider {
    /**
     * @param urls 爬去链接
     */
    void addUrlAndStart(List<AskBean> beans) throws Exception;

    /**
     * @param urls 爬去连接
     */
    void addUrl(List<AskBean> urls);
}

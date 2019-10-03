package com.xbh.wendaPlus.spider;

import java.util.List;

/**
 * 爬虫控制器
 * @author Administrator
 */
public interface ISpiderController {
    /**
     * @param urls 爬去链接
     */
    void addUrlAndStart(List<String> urls);

    /**
     * @param urls 爬去连接
     */
    void addUrl(List<String> urls);
}

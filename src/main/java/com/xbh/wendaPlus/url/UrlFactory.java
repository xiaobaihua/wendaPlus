package com.xbh.wendaPlus.url;

import lombok.Getter;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlFactory {

    @Getter
    private Map<Integer, String> articleNameMap = new HashMap<>();
    private Map<Integer, String> articleSiteAheadMap = new HashMap<>();
    private Map<Integer, String> articleSiteHinderMap = new HashMap<>();

    private String baiduUrl = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=";

    // 初始化
    public UrlFactory() {
        this.articleNameMap.put(0, "飞华健康网");
        this.articleSiteAheadMap.put(0, "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=");
        this.articleSiteHinderMap.put(0, "&si=fh21.com.cn&ct=2097152");

        this.articleNameMap.put(1, "寻医问药");
        this.articleSiteAheadMap.put(1, "http://so.xywy.com/zixun.php?keyword=");
        this.articleSiteHinderMap.put(1, "&src=so");

        this.articleNameMap.put(2, "百度文库");
        this.articleSiteAheadMap.put(2, "https://wenku.baidu.com/search?word=");
        this.articleSiteHinderMap.put(2, "");
    }

    public String production(String title, String site) throws UnsupportedEncodingException {
        String url = baiduUrl + URLEncoder.encode(title, "UTF-8") + URLEncoder.encode(site, "UTF-8");
        return url;
    }

    public String getArticleUrl(String title, int siteID) throws UnsupportedEncodingException {
        String siteAhead = this.articleSiteAheadMap.get(siteID);
        String siteHinder = this.articleSiteHinderMap.get(siteID);
        String site =siteAhead + URLEncoder.encode( title, Charset.defaultCharset().toString()) + siteHinder;
        return site;
    }
}

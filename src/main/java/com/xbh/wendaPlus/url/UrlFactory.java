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
        this.articleSiteAheadMap.put(0, "http://so.fh21.com.cn/?kw=");
        this.articleSiteHinderMap.put(0, "&type=article");

        this.articleNameMap.put(1, "寻医问药");
        this.articleSiteAheadMap.put(1, "http://so.xywy.com/zixun.php?keyword=");
        this.articleSiteHinderMap.put(1, "&src=so");
    }

    public String production(String title, String site) throws UnsupportedEncodingException {
        String url = baiduUrl + URLEncoder.encode(title, "UTF-8") + URLEncoder.encode(site, "UTF-8");
        return url;
    }

    public String getArticleUrl(String title, int siteID) throws UnsupportedEncodingException {
        String siteAhead = this.articleSiteAheadMap.get(siteID);
        String siteHinder = this.articleSiteHinderMap.get(siteID);
        String site = URLEncoder.encode(siteAhead + title + siteHinder, Charset.defaultCharset().toString());
        return site;
    }
}

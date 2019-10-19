package com.xbh.wendaPlus.url;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class UrlFactory {

    private String baiduUrl = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=";

    public String production(String title, String site) throws UnsupportedEncodingException {
        String url = baiduUrl + URLEncoder.encode(title, "UTF-8") + URLEncoder.encode(site, "UTF-8");
        return url;
    }
}

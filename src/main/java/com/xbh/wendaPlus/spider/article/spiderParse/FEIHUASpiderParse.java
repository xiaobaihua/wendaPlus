package com.xbh.wendaPlus.spider.article.spiderParse;

import com.xbh.wendaPlus.bean.ArticleBean;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @Author: xbh
 * @Date: 2019/12/27  10:42
 * @describe:
 **/
public class FEIHUASpiderParse {

    public void parse(ArticleBean bean, Document document) {
        Elements selects = document.select(".result");
        if (selects != null && selects.size() > 0) {
            for (Element element : selects) {
                Elements aTags = element.select(".t a");
                if (aTags != null && aTags.size() > 0) {
                    String href = aTags.get(0).attr("href");
                    bean.getTwoUrlList().add(href);
                }
            }
        }
    }
}

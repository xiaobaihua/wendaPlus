package com.xbh.wendaPlus.spider.article.spiderParse;

import com.xbh.wendaPlus.bean.ArticleBean;
import javafx.scene.chart.StackedBarChart;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * @Author: xbh
 * @Date: 2019/12/28  14:02
 * @describe:
 **/
public class BAIDUBAIKESpiderParse {
    public ArrayList<String> parse(Document document) {
        ArrayList<String> strings = new ArrayList<>();

        Elements dts = document.select("dt.logFirstClickTime");
        for (Element dt : dts) {
            String href = dt.select("a").attr("href");
            strings.add(href);
        }

        return strings;
    }

    public String parseContent(Document document) {
        StringBuffer buffer = new StringBuffer();

        Elements pTags = document.select(".ie-fix").select("p");
        for (Element tag : pTags) {
            String s = tag.html();
            System.out.println(s.length());
//            if (s.length() < )
            buffer.append(tag.html());
        }

        return buffer.toString();
    }
}

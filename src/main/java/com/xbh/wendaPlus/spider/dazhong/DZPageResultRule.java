package com.xbh.wendaPlus.spider.dazhong;

import cn.edu.hfut.dmic.webcollector.model.Page;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.bean.vo.PageResultVO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DZPageResultRule {
    public void parseXiaoBian(Page page, AskBean askBean, int i, int i1) {
        PageResultVO vo = askBean.getPageResultVOList();
        if (vo == null) {
            vo = new PageResultVO();
            askBean.setPageResultVOList(vo);
        }

        getResultXiaoBian(vo, page);
    }

    public void parseShengHuoYangSheng(Page page, AskBean askBean, int i, int i1) {
        PageResultVO vo = askBean.getPageResultVOList();
        if (vo == null) {
            vo = new PageResultVO();
            askBean.setPageResultVOList(vo);
        }

        getResultShengHuoYangSheng(vo, page);
    }

    public void parseRenQun(Page page, AskBean askBean, int minLen, int maxLen) {
        PageResultVO vo = askBean.getPageResultVOList();
        if (vo == null) {
            vo = new PageResultVO();
            askBean.setPageResultVOList(vo);
        }

        getResultRenQun(page, vo, minLen, maxLen);
    }

    private PageResultVO getResultXiaoBian(PageResultVO vo, Page page) {
        Elements select = page.select("div.answer + p");

        if (select.size() == 0) {
            select = page.select("body > article.con > div.main > article > article.ask > div > div > p:nth-child(3)");
        }

        if (select != null && select.size() > 0) {
            Element element = select.get(0);
            String text = element.text();
            if (text.equals("") || text.length() < 5) {
                for (Element el: select) {
                    text += el.text();
                }
            }
            vo.getResult().add(text.replace("文章导读", ""));
        }
        return vo;
    }

    private PageResultVO getResultShengHuoYangSheng(PageResultVO vo, Page page) {
        Elements select = page.select("div.content_text p");
        if (select != null && select.size() > 0) {
            Element element = select.get(0);
            String text = element.text();
            if (text.equals("") || text.length() < 5) {
                for (Element el: select) {
                    text += el.text();
                }
            }
            vo.getResult().add(text.replace("文章导读", ""));
        }

        return vo;
    }

    public PageResultVO getResultRenQun(Page page, PageResultVO vo, int i, int i1) {
        Elements select = page.select("#wrap p");
        if (select.size() == 0) {
            select = page.select("div.content_text");
        }
        if (select != null && select.size() > 0) {
            Element element = select.get(0);
            String text = element.text();
            if (text.equals("") || text.length() < 5) {
                for (Element el: select) {
                    text += el.text();
                }
            }
            vo.getResult().add(text.replace("文章导读", ""));
        }

        return vo;
    }
}

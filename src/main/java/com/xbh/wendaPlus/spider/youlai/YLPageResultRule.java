package com.xbh.wendaPlus.spider.youlai;

import cn.edu.hfut.dmic.webcollector.model.Page;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.bean.vo.PageResultVO;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class YLPageResultRule {
    public void parse(Page page, AskBean askBean, int minLen, int maxLen) {
        PageResultVO vo = askBean.getPageResultVOList();
        if (vo == null) {
            vo = new PageResultVO();
            askBean.setPageResultVOList(vo);
            vo.setTitle(askBean.getTitle());
        }
        getResult(page, vo, minLen, maxLen);
        getIssue(page, vo, minLen, maxLen);
    }

    public void parse(Document page, AskBean askBean, int minLen, int maxLen) {
        PageResultVO vo = askBean.getPageResultVOList();
        if (vo == null) {
            vo = new PageResultVO();
            askBean.setPageResultVOList(vo);
            vo.setTitle(askBean.getTitle());
        }
        getResult(page, vo, minLen, maxLen);
        getIssue(page, vo, minLen, maxLen);
    }

    private PageResultVO getResult(Page page, PageResultVO vo, int min, int max) {
        Elements select = page.select("div.text");
        // 结果展示页有两种情况
        // 第一种情况
        if (select != null && select.size() > 0) {
            String result = select.text();
            vo.getResult().add(result);
        } else {
            // 第二种
            select = page.select("div.docYes");
            if (select != null && select.size() > 0) {
                Elements p = select.get(0).select("p");
                if (p != null) {
                    String result = p.text();
                    vo.getResult().add(result);
                }

            }
        }
        return vo;
    }

    private PageResultVO getResult(Document page, PageResultVO vo, int min, int max) {
        Elements select = page.select("div.text");
        // 结果展示页有两种情况
        // 第一种情况
        if (select != null && select.size() > 0) {
            String result = select.text();
            vo.getResult().add(result);
        } else {
            // 第二种
            select = page.select("div.docYes");
            if (select != null && select.size() > 0) {
                Elements p = select.get(0).select("p");
                if (p != null) {
                    String result = p.text();
                    vo.getResult().add(result);
                }

            }
        }
        return vo;
    }

    private PageResultVO getIssue(Page page, PageResultVO vo, int min, int max) {
        // 该页面默认没有
        vo.getIssue().add("");
        return vo;
    }

    private PageResultVO getIssue(Document page, PageResultVO vo, int min, int max) {
        // 该页面默认没有
        vo.getIssue().add("");
        return vo;
    }
}

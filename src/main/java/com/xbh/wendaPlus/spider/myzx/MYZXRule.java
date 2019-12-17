package com.xbh.wendaPlus.spider.myzx;

import cn.edu.hfut.dmic.webcollector.model.Page;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.bean.vo.PageResultVO;
import org.jsoup.select.Elements;

/**
 * @Author: xbh
 * @Date: 2019/10/29  20:22
 * @describe:
 **/
public class MYZXRule {

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

    public void parseVideoPage(Page page, AskBean askBean, int minLen, int maxLen) {
        PageResultVO vo = askBean.getPageResultVOList();
        if (vo == null) {
            vo = new PageResultVO();
            askBean.setPageResultVOList(vo);
            vo.setTitle(askBean.getTitle());
        }
        getResultByVideoPage(page, vo, minLen, maxLen);
        getIssue(page, vo, minLen, maxLen);
    }

    private PageResultVO getResult(Page page, PageResultVO vo, int min, int max) {
        Elements selects = page.select("div.vd-desc");
        if (selects != null && selects.size() > 0) {
            String result = selects.get(0).text();
            vo.getResult().add(result);
        }
        return vo;
    }

    private PageResultVO getResultByVideoPage(Page page, PageResultVO vo, int min, int max) {
        Elements selects = page.select("div.videoIntroduce");
        if (selects != null && selects.size() > 0) {
            String result = selects.get(0).text();
            vo.getResult().add(result);
        }
        return vo;
    }

    private PageResultVO getIssue(Page page, PageResultVO vo, int min, int max) {
        // 该网站没有问题描述
        vo.getIssue().add("");
        return vo;
    }
}

package com.xbh.wendaPlus.spider.baidu;

import cn.edu.hfut.dmic.webcollector.model.Page;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.bean.vo.PageResultVO;
import com.xbh.wendaPlus.cofig.AssembleDict;
import com.xbh.wendaPlus.utils.MyStringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ZDPageResultRule {

    public void parse(Page page, AskBean askBean, int i, int i1) {
        PageResultVO vo = askBean.getPageResultVOList();
        if (vo == null) {
            vo = new PageResultVO();
            askBean.setPageResultVOList(vo);
            vo.setTitle(askBean.getTitle());
        }

        gerResult(vo, page);
    }

    private PageResultVO gerResult(PageResultVO vo, Page page) {
        Elements select = page.select("div.answer-text");
        if (select != null && select.size() > 0) {
            for (Element element : select) {
                String result = element.text().replace("展开全部", "");
                vo.getResult().add(result);
            }
        }

        return vo;
    }
}

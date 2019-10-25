package com.xbh.wendaPlus.spider.kuaisu;

import cn.edu.hfut.dmic.webcollector.model.Page;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.bean.vo.PageResultVO;
import com.xbh.wendaPlus.cofig.AssembleDict;
import com.xbh.wendaPlus.utils.MyStringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;

public class KSPageResultRule {
    public void parse(Page page, AskBean askBean, int minLen, int maxLen) {
        PageResultVO vo = askBean.getPageResultVOList();
        if (vo == null) {
            vo = new PageResultVO();
            askBean.setPageResultVOList(vo);
            vo.setTitle(askBean.getTitle());
        }

        getBeanIssueByPage(vo, page, minLen, maxLen);
        getSexByPage(vo, page);
        getBeanResultByPage(vo, page);
    }

    private PageResultVO getSexByPage(PageResultVO vo, Page page) {
        String age = "";
        String sex = "";

        final Elements select = page.select("div.b_askab1");
        if (select.size() > 0) {
            final String first = select.get(0).selectFirst("span").text();
            final String[] strings = first.split("|");
            if (strings != null) {
                sex = strings[0];
                age = strings[4] + strings[5];
                vo.setAge(age);
                vo.setSex(sex);
            }
        }
        return vo;
    }

    private PageResultVO getBeanIssueByPage(PageResultVO vo, Page page, int minLen, int maxLen) {
        String s = "";
        final Elements i = page.select("div.b_askcont");
        if (i.size() > 0){
            final Element issue = i.get(0);
            final Elements p = issue.select("p.crazy_new");
            if (p == null) {
                return null;
            }
            s = p.text();
            s = s.trim();
            s = s.replace("?", "。");
            s = s.replace("？", "。");
            s = s.replace(" ", ",");
            // 增加最后标点符号
            s = MyStringUtils.addLastSymbolI(s);

            // 无用词判断
            for (String string : AssembleDict.issueString) {
                s = s.replace(string, "");
            }
            // 无用标点符号判断
            for (String string : AssembleDict.failingChar) {
                s = s.replace(string, ",");
            }

            s = s.replace("?", "。");
            vo.getIssue().add(s);
        }
        return vo;
    }

    private PageResultVO getBeanResultByPage(PageResultVO vo, Page page) {
        String s = "";
        final Elements b_answerbox = page.select("div.b_answerbox");
        if (b_answerbox.size() > 0) {
            final Elements b_answerli = b_answerbox.get(0).select("div.crazy_new");
            for (Element element : b_answerli) {

                s = element.getElementsByTag("p").text();

                s = s.trim();
                s = s.replace(" ", "，");
                s = s.replace("来看", "");
                s = s.replace("或者", "，");

                if (s.indexOf("想得到的帮助") != -1) {
                    continue;
                }

                if (Pattern.matches(".*以上.*问题的建议.*", s) || Pattern.matches(".*很高兴.*", s)) {
                    continue;
                }
                // 无用词判断
                for (String string : AssembleDict.filteStrings) {
                    s = s.replace(string, "");
                }


                s = s.replace("?", "。");
                s = s.replace("？", "。");
                s = s.replace("你好", "");
                s = s.replace("您好", "");
                s = s.replace("检查结果", "结果");
                // 字数判断
                vo.getResult().add(s);
            }
        }
        return vo;
    }
}

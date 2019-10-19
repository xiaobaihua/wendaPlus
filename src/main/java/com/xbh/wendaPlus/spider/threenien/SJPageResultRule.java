package com.xbh.wendaPlus.spider.threenien;

import cn.edu.hfut.dmic.webcollector.model.Page;
import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.bean.vo.PageResultVO;
import com.xbh.wendaPlus.cofig.AssembleDict;
import com.xbh.wendaPlus.utils.MyStringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SJPageResultRule {

    public void parse(Page page, AskBean askBean, int minLen, int maxLen) {
        PageResultVO vo = askBean.getPageResultVOList();
        if (vo == null) {
            vo = new PageResultVO();
            askBean.setPageResultVOList(vo);
        }

        getBeanIssueByPage(vo, page, minLen, maxLen);
        getSexByPage(vo, page, minLen, maxLen);
        getBeanResultByPage(vo, page, minLen, maxLen);
    }

    private PageResultVO getBeanIssueByPage(PageResultVO vo, Page page, int min, int max) {
        String s = "";
        final Elements i = page.select("div.ask_hid");
        if (i.size() > 0){
            final Element issue = i.get(0);
            final Elements p = issue.select("p.txt_ms");
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

    private PageResultVO getSexByPage(PageResultVO vo, Page page, int min, int max) {
        vo.setAge("-1");
        vo.setSex("无");
        // 该网站不允许

//        final Elements i = page.select("div.ask_hid");
//        if (i.size() > 0) {
//            final Element issue = i.get(0);
//            final Elements p = issue.select("p.txt_ms");
//            if (p == null) {
//                return null;
//            }
//            final Elements select = page.select("p.mation span");
//            vo.setAge(select.get(1).text());
//            vo.setSex(select.get(0).text());
//        }
        return vo;
    }

    private PageResultVO getBeanResultByPage(PageResultVO vo, Page page, int min, int max) {
        String s = "";
        final Elements b_answerbox = page.select("div.cont_l");
        if (b_answerbox.size() > 0) {
            final Elements b_answerli = b_answerbox.get(0).select("p.sele_txt");
            for (Element element : b_answerli) {
                s = b_answerli.text();
                // 字符数量如果小于80， 或者逗号数量小于5

                s = s.trim();
                s = s.replace(" ", "，");
                s = s.replace("来看", "");
                s = s.replace("或者", "，");

//					if (s.length() < 80 ||( MyStringUtils.containsStromgCount(s, "，") < 5 || MyStringUtils.containsStromgCount(s, ",") < 5) ) {
//						continue;
//					}

                if (s.indexOf("想得到的帮助") != -1) {
                    continue;
                }

                if (s.length() < 80 ) {
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
            }
            vo.getResult().add(s);
        }
        return vo;
    }
}

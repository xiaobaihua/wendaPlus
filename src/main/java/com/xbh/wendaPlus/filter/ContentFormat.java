package com.xbh.wendaPlus.filter;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author: xbh
 * @Date: 2019/12/29  14:33
 * @describe:
 **/
public class ContentFormat {
    public static String formatContent(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        }

        // 删除所有html标签
        content = removeHtmlTag(content);

        if (content.indexOf("竭诚为您提供优质文档/双击可除") > 0) {
            content = content.replace("竭诚为您提供优质文档/双击可除", "");
        }
        if (content.indexOf("……………………………………………………………医药资料推荐…………………………………………………") > 0) {
            content = content.replace("……………………………………………………………医药资料推荐…………………………………………………", "");
        }
        if (content.indexOf("&nbsp;") > 0) {
            content = content.replace("&nbsp;", "");
        }
        content = getWordsAfter(content, "导语：");
        content = getWordsAfter(content, "文章导读：");
        content = getWordsAfter(content, "–独家原创");
        content = getWordsAfter(content, "接下来我们继续阅读。");
        content = getWordsAfter(content, "文章导读");
        content = getWordsAfter(content, "可购买打赏，谢谢");
        content = getWordsAfter(content, "摘要：");
        content = getWordsAfter(content, "/双击可除");

        // 正则处理一些东西
        content = content.replaceAll("第\\d\\s?页", "");
        // 处理a标签
//        content = content.replaceAll("<a.*>", "");
//        content = content.replaceAll("第\\d\\s?页", "");
        return content;
    }

    public static String  formatContent(List<String> contentList) {
        String content = null;
        for (String s : contentList) {
            content = formatContent(s);
        }
        
//        for (int i = 0; i < contentList.size(); i++) {
//            content = contentList.get(i);
//            // 删除所有html标签
//            content = removeHtmlTag(content);
//
//
//            if (content.indexOf("竭诚为您提供优质文档/双击可除") > 0) {
//                content = content.replace("竭诚为您提供优质文档/双击可除", "");
//            }
//            if (content.indexOf("……………………………………………………………医药资料推荐…………………………………………………") > 0) {
//                content = content.replace("……………………………………………………………医药资料推荐…………………………………………………", "");
//            }
//            if (content.indexOf("&nbsp;") > 0) {
//                content = content.replace("&nbsp;", "");
//            }
//            content = getWordsAfter(content, "导语：");
//            content = getWordsAfter(content, "文章导读：");
//            content = getWordsAfter(content, "–独家原创");
//            content = getWordsAfter(content, "接下来我们继续阅读。");
//            content = getWordsAfter(content, "文章导读");
//            content = getWordsAfter(content, "可购买打赏，谢谢");
//            content = getWordsAfter(content, "摘要：");
//
//            // 正则处理一些东西
//            content = content.replaceAll("第\\d\\s?页", "");
//            // 处理a标签
//            content = content.replaceAll("<a.*>", "");
//            content = content.replaceAll("第\\d\\s?页", "");
//
//
//        }
//
//        contentList = null;
        return content;
    }

    private static String getWordsAfter(String target, String words) {
        String[] strings = target.split(words);
        if (strings.length > 1) {
            // 只要单词后的内容
            for (int j = 1; j < strings.length; j++) {
                target = "";
                target += strings[j];
            }
        }

        return target;
    }

    private static String removeHtmlTag(String string) {
        while (string.indexOf("<") > -1) {
            int startIndex = string.indexOf("<");
            int closest = string.indexOf(">");
            String substring = string.substring(startIndex, closest+1);
            string = StringUtils.remove(string, substring);
        }

        return string;
    }
}

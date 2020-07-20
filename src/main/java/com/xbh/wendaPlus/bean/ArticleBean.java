package com.xbh.wendaPlus.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xbh
 * @Date: 2019/12/25  13:40
 * @describe:
 **/
@Data
public class ArticleBean {
    // 基础内容
    private String wordsID;
    private String title;
    private String content;

    private List<String> oneUrlList = new ArrayList();
    private List<String> twoUrlList = new ArrayList<>();

    private List<String> contentList = new ArrayList<>();

}

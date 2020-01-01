package com.xbh.wendaPlus.cofig;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xbh
 * @Date: 2019/12/26  15:43
 * @describe:
 **/
public class ArticleUIConfig {
    public static Map<String, Integer> articleNameMap = new HashMap<>();

    static {
        articleNameMap.put("飞华健康网", 0);
        articleNameMap.put("寻医问药", 1);
        articleNameMap.put("百度文库", 2);
    }
}

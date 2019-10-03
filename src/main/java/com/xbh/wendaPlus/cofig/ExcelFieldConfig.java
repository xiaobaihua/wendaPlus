package com.xbh.wendaPlus.cofig;

import javafx.util.Pair;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.HashMap;
import java.util.Map;

public class ExcelFieldConfig {
    //    NO("序号"), TITLE("标题"), RESULT_GEGIN_INDEX("答案1"), SEX("性别"), AGE("年龄"), RESULT("答案"),
//    ;
//    序号, 标题, 答案1, 性别, 年龄, 答案
    public static Map<String, MutablePair<String, Integer>> CYTranslation = new HashMap();

    static {
        CYTranslation.put("excelRowIndex", new MutablePair<>("期号", -1));
        CYTranslation.put("no", new MutablePair<>("序号", -1));
        CYTranslation.put("title", new MutablePair<>("标题", -1));
        CYTranslation.put("title1", new MutablePair<>("题目", -1));
        CYTranslation.put("sex", new MutablePair<>("性别", -1));
        CYTranslation.put("age", new MutablePair<>("年龄", -1));
        CYTranslation.put("issue", new MutablePair<>("描述", -1));
        CYTranslation.put("result", new MutablePair<>("答案", -1));
        CYTranslation.put("resultBegin", new MutablePair<>("答案1", -1));
    }
}

package com.xbh.wendaPlus.filter;

import com.xbh.wendaPlus.bean.vo.PageResultVO;
import com.xbh.wendaPlus.cofig.SettingConfig;
import com.xbh.wendaPlus.utils.MyStringUtils;
import java.util.ArrayList;
import java.util.List;

public class GenerateFinalResult {
    // 生成最终结果
    public String generateResult(PageResultVO vo) {
        Integer lengthMin = Integer.valueOf(SettingConfig.getResultWordsLengthMin());
        Integer lengthMax = Integer.valueOf(SettingConfig.getResultWordsLengthMax());
        if (vo != null) {
            List<String> resultList = vo.getResult();
            if (resultList.size() > 0) {
                for (String result : resultList) {
                    Integer exclusiveSymbolStrLength = MyStringUtils.getExclusiveSymbolStrLength(result);
                    if (exclusiveSymbolStrLength > lengthMin && exclusiveSymbolStrLength < lengthMax) {
                        return result;
                    }
                }
            }
        }
        return "";
    }

    // 生成最终结果
    public List<String> generateResultList(PageResultVO vo) {
        if (vo != null) {
            List<String> result = vo.getResult();
            ArrayList<String> strings = new ArrayList<>();
            int curInt = 0;
            for (String s : result) {
                if (curInt == 3) {
                    return strings;
                }
                strings.add(s);
                curInt++;
            }
            return strings;
        }

        return null;
    }

    public String generateIssue(PageResultVO vo) {
        Integer lengthMin = Integer.valueOf(SettingConfig.getIssueWordsLengthMin());
        Integer lengthMax = Integer.valueOf(SettingConfig.getIssueWordsLengthMax());
        if (vo != null) {
            List<String> issues = vo.getIssue();
            if (issues.size() > 0) {
                for (String issue : issues) {
                    Integer exclusiveSymbolStrLength = MyStringUtils.getExclusiveSymbolStrLength(issue);
                    if (exclusiveSymbolStrLength > lengthMin && exclusiveSymbolStrLength < lengthMax) {
                        String s = MyStringUtils.deleteFirstSymbol(issue);
                        return MyStringUtils.deleteRepeatSymbol1(s);
                    }
                }
            }
        }
        return "";
    }
}

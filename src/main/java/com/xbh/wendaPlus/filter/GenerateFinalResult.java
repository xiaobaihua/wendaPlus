package com.xbh.wendaPlus.filter;

import com.xbh.wendaPlus.bean.vo.PageResultVO;
import com.xbh.wendaPlus.cofig.SettingConfig;
import com.xbh.wendaPlus.utils.MyStringUtils;
import javafx.util.Pair;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GenerateFinalResult {

    // 生成最终结果
    public String generateResult(PageResultVO vo) {
        if (vo != null) {
            if (SettingConfig.getResultMode().equals("数量优先")) {
                List<String> resultList = vo.getResult();
                String maxLengthStrByList = getMaxLengthStrByList(resultList);
                return MyStringUtils.deleteFirstSymbol(maxLengthStrByList);
            } else if (SettingConfig.getResultMode().equals("质量优先")) {
                NPLFilter filter = new NPLFilter();
                List<Pair> resultListSameRate = filter.getResultListSameRate(vo.getTitle(), vo.getResult());
                if (resultListSameRate != null && resultListSameRate.size() > 0) {
                    String s = resultListSameRate.get(0).getValue().toString();
                    return MyStringUtils.deleteFirstSymbol(s);
                }
            } else if (SettingConfig.getResultMode().equals("字数限制")) {
                Integer lengthMin = Integer.valueOf(SettingConfig.getResultWordsLengthMin());
                Integer lengthMax = Integer.valueOf(SettingConfig.getResultWordsLengthMax());
                if (vo != null) {
                    List<String> resultList = vo.getResult();
                    if (resultList.size() > 0) {
                        for (String result : resultList) {
                            Integer exclusiveSymbolStrLength = MyStringUtils.getExclusiveSymbolStrLength(result);
                            if (exclusiveSymbolStrLength > lengthMin && exclusiveSymbolStrLength < lengthMax) {
                                return MyStringUtils.deleteFirstSymbol(result);
                            }
                        }
                    }
                }
            }
        }

        return "";
    }

    // 生成最终结果
    public List<String> generateResultList(PageResultVO vo) {
        if (vo != null) {
            if (SettingConfig.getResultMode().equals("数量优先")) {
                List<String> resultList = vo.getResult();
                List<String> desOrderList = desOrderList(resultList);

                for (int i = 0; i < desOrderList.size(); i++) {
                    String deleteFirstSymbol = MyStringUtils.deleteFirstSymbol(desOrderList.get(i));
                    desOrderList.set(i, deleteFirstSymbol);
                }
                
                return desOrderList;
            } else if (SettingConfig.getResultMode().equals("质量优先")) {
                List<String> strList = new ArrayList<>();
                NPLFilter filter = new NPLFilter();
                List<Pair> resultListSameRate = filter.getResultListSameRate(vo.getTitle(), vo.getResult());
                if (resultListSameRate != null) {
                    for (Pair pair : resultListSameRate) {
                        strList.add(MyStringUtils.deleteFirstSymbol(pair.getValue().toString()));
                    }
                    return strList;
                }
            } else if (SettingConfig.getResultMode().equals("字数限制")) {
                Integer lengthMin = Integer.valueOf(SettingConfig.getResultWordsLengthMin());
                Integer lengthMax = Integer.valueOf(SettingConfig.getResultWordsLengthMax());
                if (vo != null) {
                    List<String> result = vo.getResult();
                    ArrayList<String> strings = new ArrayList<>();
                    int curInt = 0;
                    for (String s : result) {
                        if (curInt == 3) {
                            return strings;
                        }
                        if (s.length() > lengthMin && s.length() < lengthMax){
                            s = MyStringUtils.deleteFirstSymbol(s);

                            strings.add(s);
                            curInt++;
                        }
                    }
                    return strings;
                }
            }
        }


        return null;

//        if (vo != null) {
//            List<String> result = vo.getResult();
//            ArrayList<String> strings = new ArrayList<>();
//            int curInt = 0;
//            for (String s : result) {
//                if (curInt == 3) {
//                    return strings;
//                }
//                strings.add(s);
//                curInt++;
//            }
//            return strings;
//        }

    }

    public String generateIssue(PageResultVO vo) {
        if (vo != null) {
            if (SettingConfig.getIssueMode().equals("数量优先")) {
                List<String> issueList = vo.getIssue();
                String maxLengthStrByList = getMaxLengthStrByList(issueList);

                return MyStringUtils.deleteFirstSymbol(maxLengthStrByList);
            } else if (SettingConfig.getIssueMode().equals("质量优先")) {
                NPLFilter filter = new NPLFilter();
                List<Pair> issueListSameRate = filter.getIssueListSameRate(vo.getTitle(), vo.getIssue());
                if (issueListSameRate != null && issueListSameRate.size() > 0) {
                    String s = issueListSameRate.get(0).getValue().toString();
                    return MyStringUtils.deleteFirstSymbol(s);
                }

            } else if (SettingConfig.getIssueMode().equals("字数限制")) {
                Integer lengthMin = Integer.valueOf(SettingConfig.getIssueWordsLengthMin());
                Integer lengthMax = Integer.valueOf(SettingConfig.getIssueWordsLengthMax());
                if (vo != null) {
                    List<String> issueList = vo.getIssue();
                    if (issueList.size() > 0) {
                        for (String issue : issueList) {
                            Integer exclusiveSymbolStrLength = MyStringUtils.getExclusiveSymbolStrLength(issue);
                            if (exclusiveSymbolStrLength > lengthMin && exclusiveSymbolStrLength < lengthMax) {
                                return MyStringUtils.deleteFirstSymbol(issue);
                            }
                        }
                    }
                }
            }
        }





//        Integer lengthMin = Integer.valueOf(SettingConfig.getIssueWordsLengthMin());
//        Integer lengthMax = Integer.valueOf(SettingConfig.getIssueWordsLengthMax());
//        if (vo != null) {
//            List<String> issues = vo.getIssue();
//            if (issues.size() > 0) {
//                String maxLengthStrByList = getMaxLengthStrByList(issues);
//                String s = MyStringUtils.deleteFirstSymbol(maxLengthStrByList);
//                return MyStringUtils.deleteRepeatSymbol1(s);
//
////                for (String issue : issues) {
////                    Integer exclusiveSymbolStrLength = MyStringUtils.getExclusiveSymbolStrLength(issue);
////                    if (exclusiveSymbolStrLength > lengthMin && exclusiveSymbolStrLength < lengthMax) {
////                        String s = MyStringUtils.deleteFirstSymbol(issue);
////                        return MyStringUtils.deleteRepeatSymbol1(s);
////                    }
////                }
//            }
//        }
        return "";
    }


    private String getMaxLengthStrByList(List<String> strings) {
        if (strings.size() > 0) {
            strings.sort(((o1, o2) -> o2.length() - o1.length()));
            return strings.get(0);
        }

        return null;
    }

    private List<String> desOrderList(List<String> strings) {
        if (strings.size() > 0) {
            strings.sort(((o1, o2) -> o2.length() - o1.length()));
        }
        return strings;
    }
//    // 过滤
//    private List
}

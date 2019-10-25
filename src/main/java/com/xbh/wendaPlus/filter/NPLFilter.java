package com.xbh.wendaPlus.filter;


import com.xbh.wendaPlus.bean.vo.PageResultVO;
import javafx.util.Pair;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NPLFilter {

    public List<Pair> getIssueListSameRate(String title, List<String> issues) {
        ArrayList<Pair> pairs = new ArrayList<>();
        if (title != null && issues != null) {
            for (String issue : issues) {
                Pair<Integer, List> pair = patternIssueString(title, issue);
                pairs.add(pair);
            }
        }

        // 排序
        return wordPairSort(pairs);
    }

    public List<Pair> getResultListSameRate(String title, List<String> results) {
        ArrayList<Pair> pairs = new ArrayList<>();
        if (title != null && results != null) {
            for (String result : results) {
                Pair<Integer, List> pair = patternIssueString(title, result);
                pairs.add(pair);
            }
        }

        // 排序
        return wordPairSort(pairs);
    }

    private Pair<Integer, List> patternResultString(String title, String result) {
        List<Word> titleWord = null;
        List<Word> issueList = null;

        // 获得标题和描述词组
        titleWord = WordSegmenter.seg(title);
        issueList = getIssueWordList(result);

        Pair<Integer, List> pair = new Pair(getSameRate(titleWord, issueList), result);

        return pair;
    }

    private Pair<Integer, List> patternIssueString(String title, String issue) {
        List<Word> titleWord = null;
        List<Word> issueList = null;

        // 获得标题和描述词组
        titleWord = WordSegmenter.seg(title);
        issueList = getIssueWordList(issue);

        Pair<Integer, List> pair = new Pair(getSameRate(titleWord, issueList), issue);

        return pair;
    }

    private List wordPairSort(List<Pair> list) {
        list.sort(((o1, o2) -> {
            Object key1 = o1.getKey();
            Object key2 = o2.getKey();

            if (key1 instanceof Number && key2 instanceof Number) {
                return Integer.valueOf(key2.toString()) - Integer.valueOf(key1.toString());
            }
            return 0;
        }));
        return list;
    }

    private int getSameRate(List<Word> words1, List<Word> words2) {
        int rate = 0;

        for (Word word : words1) {
            for (Word word1 : words2) {
                if (word.equals(word1)) {
                    rate++;
                }
            }
        }

        return rate;
    }

    private List<Word> getIssueWordList(String issue) {
        if (issue != null) {
            return WordSegmenter.seg(issue);
        }
        return null;
    }
}

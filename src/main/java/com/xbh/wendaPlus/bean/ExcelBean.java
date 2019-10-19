package com.xbh.wendaPlus.bean;

import com.xbh.wendaPlus.bean.vo.PageResultVO;
import com.xbh.wendaPlus.filter.GenerateFinalResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelBean {
    private String excelRowIndex = null;
    private String no = null;
    private String title = null;
    private String title1 = null;
    private String sex = null;
    private String age = null;
    private String issue = null;
    private String result = null;
    private String resultBegin = null;
    private List resultList = new ArrayList<String>();


    public ExcelBean(AskBean askBean) {
        this.setExcelRowIndex(askBean.getExcelRowIndex());
        this.title = askBean.getTitle();
        this.title1 = askBean.getTitle1();
        this.setNo(askBean.getNo());
        if (askBean.getPageResultVOList() != null) {
            this.setAge(askBean.getPageResultVOList().getAge());
        }

        if (askBean.getPageResultVOList() != null) {
            this.setIssue(askBean.getPageResultVOList());
        }
        this.setResult(askBean.getPageResultVOList());
        this.setTitle(askBean.getTitle());
        this.setTitle1(askBean.getTitle1());
        if (askBean.getPageResultVOList() != null) {
            this.setSex(askBean.getPageResultVOList().getSex());
        }
        this.setResultList(askBean.getPageResultVOList());
        this.setResultBegin(askBean.getResultBegin());
    }

    public void setResult(PageResultVO vo) {
        GenerateFinalResult finalResult = new GenerateFinalResult();
        String result = finalResult.generateResult(vo);
        this.result = result;
    }

    public void setIssue(PageResultVO vo) {
        GenerateFinalResult finalResult = new GenerateFinalResult();
        String issue = "";
        if (title != null) {
            issue = finalResult.generateIssue(vo) + title;
        } else if (title1 != null) {
            issue = finalResult.generateIssue(vo) + title1;
        }
        this.issue = issue;
    }

    public void setResultList(PageResultVO vo) {
        GenerateFinalResult finalResult = new GenerateFinalResult();
        List<String> resultList = finalResult.generateResultList(vo);
        this.resultList = resultList;
    }
}

package com.xbh.wendaPlus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        this.setNo(askBean.getNo());
        this.setAge(askBean.getAge());
        this.setIssue(askBean.getIssue());
        this.setResult(askBean.getResult());
        this.setTitle(askBean.getTitle());
        this.setTitle1(askBean.getTitle1());
        this.setSex(askBean.getSex());
        this.setResultList(askBean.getResultList());
        this.setResultBegin(askBean.getResultBegin());
    }
}

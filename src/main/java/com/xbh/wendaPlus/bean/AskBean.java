package com.xbh.wendaPlus.bean;

import cn.edu.hfut.dmic.webcollector.model.Page;
import com.xbh.wendaPlus.bean.vo.PageResultVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 问答bean
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AskBean {
    private String excelRowIndex = null;
    private String no = null;
    private List<String> oneUrl;
    private List<String> twoUrl = new ArrayList<>();
    private List<String> threeUrl = new ArrayList<>();

    private String title = null;
    private String title1 = null;
    private String sex = null;
    private String age = null;
    private String issue = null;
    private String result = null;
    private String resultBegin = null;
    private List<String> resultList = new ArrayList<String>();
    private PageResultVO pageResultVOList = null;
    private List<Page> pages = new ArrayList<>();
}

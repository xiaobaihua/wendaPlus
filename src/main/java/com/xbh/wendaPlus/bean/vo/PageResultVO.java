package com.xbh.wendaPlus.bean.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResultVO {
    String title = "";
    String age = null;
    String sex = null;
    List<String> issue = new ArrayList<>();
    List<String> result = new ArrayList<>();
}

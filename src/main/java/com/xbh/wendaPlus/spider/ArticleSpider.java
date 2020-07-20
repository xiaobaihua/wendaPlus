package com.xbh.wendaPlus.spider;

import com.xbh.wendaPlus.bean.ArticleBean;
import com.xbh.wendaPlus.spider.article.HtmlUnit.BAIDUBAIKESpiderHtmlUnit;
import com.xbh.wendaPlus.spider.article.HtmlUnit.FEIHUASpiderHtmlUnit;
import com.xbh.wendaPlus.spider.article.HtmlUnit.XUEYIWENYAOSpiderHtmlUnit;
import com.xbh.wendaPlus.spider.spiderConfig.ArticleRunType;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xbh
 * @Date: 2019/12/26  17:52
 * @describe:
 **/
@NoArgsConstructor
public class ArticleSpider {
    private ArticleRunType runType = null;

    public ArticleSpider(ArticleRunType type) {
        this.runType = type;
    }

    public ArticleSpider(int typeIndex) {
        this.runType = ArticleRunType.values()[typeIndex];
    }

    public void spiderRun(List<ArticleBean> articleBeanList) throws Exception {
        if (this.runType != null) {
            switch (this.runType) {
                case FEIHUA:{
                    FEIHUASpiderHtmlUnit htmlUnit = new FEIHUASpiderHtmlUnit();
                    htmlUnit.getSearchData(articleBeanList);
                    break;
                }
                case XUNYIWENYAO: {
                    XUEYIWENYAOSpiderHtmlUnit htmlUnit = new XUEYIWENYAOSpiderHtmlUnit();
                    htmlUnit.getSearchData(articleBeanList);
                    break;
                }
                case BAIDUBAIKE: {
                    BAIDUBAIKESpiderHtmlUnit htmlUnit = new BAIDUBAIKESpiderHtmlUnit();
                    htmlUnit.getSearchData(articleBeanList);
                    break;
                }
                default:{
                    return;
                }
            }
        }
    }
}

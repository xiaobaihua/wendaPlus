package com.xbh.wendaPlus.filter;

import com.xbh.wendaPlus.bean.vo.PageResultVO;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NPLFilterTest {

    @Test
    void patternString() {
        ArrayList<String> list = new ArrayList<>();
        list.add("间,反复住院有3次之多,每次出院回家就发热,一般都是午后开始,典型的结核病症状,每天如此,非常有规律的,我问她,她总说是不是感冒,说我胸片拍出来蛮好的,怎么会无故发热,我也想知道,这个就是我第一个主任看的效果,后来我只有另高明了,还好,人家一看ct就查出问题的根源了,我才开始了第二次的治疗,所以,我是不是属于复治结核呢,我利福平一类的药一直过敏,用不上的,现在治疗药物是三个一线药+加替沙星+克拉霉素+力克肺疾,在去年2月底出院前做了药敏试验,发现链霉素耐药（事实上链霉素我就根本没用过）,别的在用药都不耐药,但现在报告说有菌,还是让我再做一次药敏,现在等待报告中,心里很是着急,第二次治疗至今,自我感觉还好,也没再发烧,就是有时半夜有偶尔的咳嗽。痰培养阳性是耐药吗？");
        list.add("我邻居有一个女孩，最近感觉肚子很疼，吃不下东西，去医院一检查，说是胆囊炎，听说让她住院，因为她家没有闲人，缺少护理，所以她想不住院，不知道象她这样的要怎么治疗才能好。需要多长时间。急性胆囊炎多久能治好？");
        list.add("因为发热38.6，咳嗽去医院看病，血常规正常，白细胞和中性粒细胞都不高，brc10.2,正常值1-10,x片右下肺少许斑点影，说，肺感染，这到底是病毒性还是细菌性肺炎,大夫说说原因,肺炎念球菌属于细菌还是病毒？");
        list.add("肺炎住院被查出艾滋病，用美洛、莫西沙星治疗两个月，药物没见吸收，肺炎除了积液没了，其它的也未见改善。现在吃疾控给的药，和抗菌优，还能好么。肺炎住院会不会检查hiv？");


        PageResultVO vo = new PageResultVO();
        vo.setTitle("痰培养阳性是耐药吗？");
        vo.setIssue(list);

        NPLFilter npl = new NPLFilter();

        List<Pair> issueListSameRate = npl.getIssueListSameRate(vo.getTitle(), vo.getIssue());

        System.out.println(issueListSameRate);

        List<Pair> resultList = npl.getResultListSameRate(vo.getTitle(), vo.getIssue());

        System.out.println(resultList);

    }
}
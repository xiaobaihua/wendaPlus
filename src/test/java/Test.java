import com.xbh.wendaPlus.bean.AskBean;
import com.xbh.wendaPlus.cofig.SettingConfig;
import com.xbh.wendaPlus.spider.SpiderController;
import com.xbh.wendaPlus.spider.dazhong.DZSpider;
import com.xbh.wendaPlus.utils.MyStringUtils;

import java.util.ArrayList;
import java.util.List;

public class Test {
    @org.junit.Test
    public void name() throws Exception {
        String s = MyStringUtils.deleteFirstSymbol(",截肢手术后大部分的伤口都已经愈合，有一点伤口不愈合，结痂掉了之后，又去医院进行了第二次缝合，现在两个周了还没有拆线，针眼处还有渗水的现象，我想问一下这是伤口感染。要是感染的话，怎么处理。");
        String s1 = MyStringUtils.deleteRepeatSymbol1(",截肢手术后大部分的伤口都已经愈合，有一点伤口不愈合，结痂掉了之后，又去医院进行了第二次缝合，现在两个周了还没有拆线，针眼处还有渗水的现象，我想问一下这是伤口感染。要是感染的话，怎么处理。");
        System.out.println(s + s1);
    }
}

import com.xbh.wendaPlus.filter.ContentFormat;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @Author: xbh
 * @Date: 2019/12/29  21:23
 * @describe:
 **/
public class test2 {

    @Test
    public void test() {
        String content = "如对您有帮助，可购买打赏，谢谢常识分享，对您有帮助可购买打赏人中长水泡什么原因？怎样治疗好导语：在人们的人中部位如果有水泡出现的话，大家对于人中长水泡的原因就应多多了解，那么，人中长水泡是什么原因造成的呢？人中长水泡什么原因在人们的人中部位如果有水泡出现的话，大家对于人中长水泡的原因就应多多了解，那么，人中长水泡是什么原因造成的呢？人中长水泡什么原因？可能是带状疱疹，带状疱疹是一种由水痘——带状疱疹病毒所引起的急性疱疹性皮肤病。中医称为”缠腰火龙”“缠腰火丹”，俗称”蜘蛛疮”。其主要特点为簇集水泡，沿一侧周围神经作群集带状分布，伴有明显神经痛。怎样治疗？1.药物疗法(1)抗病毒药物可选用阿昔洛韦、伐昔洛韦或泛昔洛韦。(2)神经痛药物治疗①抗抑郁药主要药物有帕罗西汀(塞乐特)、氟西汀(百优解)、氟伏草胺、舍曲林等;②抗惊厥药有卡马西平、丙戊酸钠等。③麻醉性镇痛药以吗啡为代表的镇痛药物。可供选择药物有吗啡(美施康定)、羟基吗啡酮(奥施康定)、羟考酮、芬太尼(多瑞吉)、二氢埃托菲、路盖克等。④非麻醉性镇痛药包括NSAIDs、曲吗多、乌头生物碱、辣椒碱等。2.神经阻滞重度疼痛药物难以控制时即应考虑用直接有效的感觉神经阻滞疗法。阻滞定位的选择应取决于病变范围及治疗反应。总的原则应当是从浅到深，从简单到复杂，从末梢到神经干、神经根。";
//        content = content.replaceAll("<.*>{1}", "");;
//        content = content.replaceFirst("<.*>", "");;
        ArrayList<String> list = new ArrayList<>();
        list.add(content);
        content = ContentFormat.formatContent(list);

        System.out.println(content);
    }
}

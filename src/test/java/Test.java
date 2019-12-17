import com.xbh.wendaPlus.utils.MyStringUtils;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Test {

    @org.junit.Test
    public void test3() {


    }


    @org.junit.Test
    public void test2() {
        List<String> strings = new ArrayList<>();

        strings.add("123456");
        strings.add("12345689");
        strings.add("1234");
        System.out.println(strings);


        System.out.println(strings);
    }

    @org.junit.Test
    public void name() throws Exception {
        String s = MyStringUtils.deleteFirstSymbol(",截肢手术后大部分的伤口都已经愈合，有一点伤口不愈合，结痂掉了之后，又去医院进行了第二次缝合，现在两个周了还没有拆线，针眼处还有渗水的现象，我想问一下这是伤口感染。要是感染的话，怎么处理。");
        String s1 = MyStringUtils.deleteRepeatSymbol1(",截肢手术后大部分的伤口都已经愈合，有一点伤口不愈合，结痂掉了之后，又去医院进行了第二次缝合，现在两个周了还没有拆线，针眼处还有渗水的现象，我想问一下这是伤口感染。要是感染的话，怎么处理。");
        System.out.println(s + s1);
    }

    @org.junit.Test
    public void test() {
        List<Word> title = WordSegmenter.seg("胎儿胆囊偏大可以要吗？");
        List<Word> issue = WordSegmenter.seg("怀孕24周一直到怀孕29周持续偏大,做了无创都是正常。出生了会有影响。胎儿胆囊偏大可以要吗？");
        List<Word> result = WordSegmenter.seg("不会有太大的影响，建议您孕期注意观察胎儿胎动的情况，注意做好每一次孕期检查，注意休息，不要熬夜，注意饮食，不要吃冰冷辛辣上火刺激性的食物，避免影响身体健康。");
        System.out.println(title);
        System.out.println(issue);
        System.out.println(result);
    }
}

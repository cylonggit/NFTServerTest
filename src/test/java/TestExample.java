
import com.market.bc.TestApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= TestApplication.class) // 指定spring-boot的启动类

public class TestExample {
    public static final Logger logger = LoggerFactory.getLogger(TestExample.class.getName());
//    @Autowired
//    FiscoBcosClient myFiscoClient;

    @Before //为了测试预务好链上、mysql、minio、文件等数据
    public void init(){
        System.out.println("...int the env before the test....");
    }

    @Test //测试功能，并与预期结果进行对比后并断言，断言使用asset
    public void testSomething() {
        System.out.println("...do the test here, including the assertion...");
        assert (1==1);
   }

   @After  //主要是清理一些为了测试而弄出来的东西，如链上的NFT烧掉、mysql中的东西删除，或Minio的图片删掉等
    public void afterTest(){
       System.out.println("...if need to do something after the test, you can do here....");
    }

}





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

    @Before
    public void init(){
        System.out.println("...int the env before the test....");
    }

    @Test
    public void testSomething() {
        System.out.println("...do the test here, including the assertion...");
   }

   @After
    public void afterTest(){
       System.out.println("...if need to do something after the test, you can do here....");
    }

}




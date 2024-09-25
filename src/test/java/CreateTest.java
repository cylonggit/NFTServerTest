import com.market.bc.TestApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class CreateTest {
    // 用于测试铸造NFT

    @Autowired
    RestTemplate restTemplate;

    @Before
    public void init() {
        // 用户联盟链账户已创建并拥有相应的权限


    }

}

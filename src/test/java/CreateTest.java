import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class CreateTest {
    // 用于测试铸造NFT

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    MyConfig myConfig;

    @Before
    public void init() {
        // 用户联盟链账户已创建并拥有相应的权限
        Map<String, String> params = new HashMap<>();
        params.put("username", "mytest");
        params.put("password", "123456");
        params.put("mobile", "15972133907");
        params.put("email", "test@test.com");
        restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/user/user/register", params, String.class);
    }

    @Test
    public void test() {

    }

}

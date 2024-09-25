import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class TransferTest {

    @Autowired
    MyConfig myConfig;

    RestTemplate restTemplate = new RestTemplate();

    String userID1, userID2;

    @Before
    public void initUser1() { // 初始化原所有者用户
        Map<String, String> params = new HashMap<>();
        params.put("username", "mytest");
        params.put("password", "123456");
        ResponseEntity<Map> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/user/user/login", params, Map.class);
        userID1 = ((Map) response.getBody().get("data")).get("userID").toString();
        System.out.println(userID1);
    }

    @Before
    public void initUser2() { // 初始化意向转移用户
        Map<String, String> params = new HashMap<>();
        params.put("username", "othertest");
        params.put("password", "123456");
        ResponseEntity<Map> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/user/user/login", params, Map.class);
        userID2 = ((Map) response.getBody().get("data")).get("userID").toString();
        System.out.println(userID2);
    }

    @Before
    public void initNFT() { // 找到原所有者用户的一件NFT
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("size", "10");
        params.put("userID", userID1);
        ResponseEntity<Map> response = restTemplate.getForEntity(myConfig.getBackendServerUrl() + "/nft/nft/ownNFT", Map.class, params);
        System.out.println(response.getBody());
    }

    @Test
    public void test() {

    }

}

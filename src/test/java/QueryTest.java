import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class QueryTest {
    @Autowired
    MyConfig myConfig;

    RestTemplate restTemplate = new RestTemplate();

    String userID, nftID;

    @Before
    public void init() {
        Map<String, String> params = new HashMap<>();
        params.put("username", "mytest");
        params.put("password", "123456");
        ResponseEntity<Map> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/user/user/login", params, Map.class);
        userID = ((Map) response.getBody().get("data")).get("userID").toString();
        System.out.println(userID);

        Map<String, String> params1 = new HashMap<>();
        params1.put("page", "1");
        params1.put("size", "1");
        params1.put("userID", userID);
        ResponseEntity<Map> response1 = restTemplate.getForEntity(myConfig.getBackendServerUrl() + "/nft/nft/ownNFT?page={page}&size={size}&userID={userID}", Map.class, params1);
        System.out.println(response1.getBody());
        nftID = ((Map) ((List) ((Map) response1.getBody().get("data")).get("rows")).get(0)).get("nftID").toString();
        System.out.println(nftID);
    }

}

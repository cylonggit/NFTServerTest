import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import com.market.bc.fisco.FiscoBcosClient;
import com.market.bc.pojo.NFTInfo;
import entity.Result;
import org.junit.Before;
import org.junit.Test;
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
public class NFTQueryTest {
    @Autowired
    FiscoBcosClient myFiscoClient;

    @Autowired
    MyConfig myConfig;

    RestTemplate restTemplate = new RestTemplate();

    String userID, nftID;

    @Before
    public void init() {
        Map<String, String> params = new HashMap<>();
        params.put("username", "mytest");
        params.put("password", "123456");
        ResponseEntity<Result> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/user/user/login", params, Result.class);
        userID = ((Map) response.getBody().getData()).get("userID").toString();
        System.out.println(userID);
        // 选择一个用户
        Map<String, String> params1 = new HashMap<>();
        params1.put("page", "1");
        params1.put("size", "1");
        params1.put("userID", userID);
        ResponseEntity<Map> response1 = restTemplate.getForEntity(myConfig.getBackendServerUrl() + "/nft/nft/ownNFT?page={page}&size={size}&userID={userID}", Map.class, params1);
        System.out.println(response1.getBody());
        nftID = ((Map) ((List) ((Map) response1.getBody().get("data")).get("rows")).get(0)).get("nftID").toString();
        System.out.println(nftID);
        // 准备数据，选择一个已铸造的非同质化通证，并记录其ID
    }

    @Test
    public void test() {
        Map<String, String> params1 = new HashMap<>();
        params1.put("tokenId", nftID);
        ResponseEntity<NFTInfo> response1 = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/bc/bc/getNFTInfo", params1, NFTInfo.class);
        System.out.println(response1.getBody());
        String owner = response1.getBody().getOwner();
        String files = response1.getBody().getFiles();
        String auth = response1.getBody().getAuth();
        String pubkey = response1.getBody().getPubkey();
    }
}

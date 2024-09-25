import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import com.market.bc.fisco.FiscoBcosClient;
import com.market.bc.pojo.User;
import entity.Result;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class NFTTransferTest {
    @Autowired
    FiscoBcosClient myFiscoClient;

    @Autowired
    MyConfig myConfig;

    RestTemplate restTemplate = new RestTemplate();

    String userID1, userID2, nftID, userAddress1, userAddress2;

    @Before
    public void initUser1() throws ContractException { // 初始化原所有者用户
        Map<String, String> params = new HashMap<>();
        params.put("username", "mytest");
        params.put("password", "123456");
        ResponseEntity<Map> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/user/user/login", params, Map.class);
        userID1 = ((Map) response.getBody().get("data")).get("userID").toString();
        System.out.println(userID1);

        params = new HashMap<>();
        params.put("userID", userID1);
        ResponseEntity<Result> response0 = restTemplate.getForEntity(myConfig.getBackendServerUrl() + "/user/user?userID={userID}", Result.class, params);
        userAddress1 = new User((Map) response0.getBody().getData()).getAccountAddress();
        System.out.println(userAddress1);

        // 找到原所有者用户的一件NFT
        Map<String, String> params1 = new HashMap<>();
        params1.put("page", "1");
        params1.put("size", "1");
        params1.put("userID", userID1);
        ResponseEntity<Map> response1 = restTemplate.getForEntity(myConfig.getBackendServerUrl() + "/nft/nft/ownNFT?page={page}&size={size}&userID={userID}", Map.class, params1);
        System.out.println(response1.getBody());
        nftID = ((Map) ((List) ((Map) response1.getBody().get("data")).get("rows")).get(0)).get("nftID").toString();
        System.out.println(nftID);
    }

    @Before
    public void initUser2() { // 初始化意向转移用户
        Map<String, String> params = new HashMap<>();
        params.put("username", "othertest");
        params.put("password", "123456");
        ResponseEntity<Map> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/user/user/login", params, Map.class);
        userID2 = ((Map) response.getBody().get("data")).get("userID").toString();
        System.out.println(userID2);

        params = new HashMap<>();
        params.put("userID", userID2);
        ResponseEntity<Result> response0 = restTemplate.getForEntity(myConfig.getBackendServerUrl() + "/user/user?userID={userID}", Result.class, params);
        userAddress2 = new User((Map) response0.getBody().getData()).getAccountAddress();
        System.out.println(userAddress2);
    }


    @Test
    public void test() {

    }

    @After
    public void cleanup() {

    }
}

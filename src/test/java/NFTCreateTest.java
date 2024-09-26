import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import com.market.bc.fisco.FiscoBcosClient;
import com.market.bc.pojo.User;
import entity.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import util.IdWorker;

import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class NFTCreateTest {
    RestTemplate restTemplate = new RestTemplate();
    String userID;
    String url;
    String userAddress;
    String pubkey;
    String hexPrivateKey;

    @Autowired
    MyConfig myConfig;

    @Autowired
    FiscoBcosClient myFiscoClient;

    @Autowired
    IdWorker idWorker;

    @Before
    public void init() throws URISyntaxException {
        // 用户联盟链账户已创建并拥有相应的权限
        Map<String, String> params = new HashMap<>();
        params.put("username", "mytest");
        params.put("password", "123456");
        ResponseEntity<Map> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/user/user/login", params, Map.class);
        userID = ((Map) response.getBody().get("data")).get("userID").toString();
        System.out.println(userID);
        // 获取userID

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        Path path = Paths.get(getClass().getClassLoader().getResource("images/2.jpg").toURI());
        bodyMap.add("file", new FileSystemResource(path.toFile()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<Map> response1 = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/file/file/upload", requestEntity, Map.class);
        url = response1.getBody().get("url").toString();
        System.out.println(url);
        // 上传图片

        params = new HashMap<>();
        params.put("userID", userID);
        ResponseEntity<Result> response0 = restTemplate.getForEntity(myConfig.getBackendServerUrl() + "/user/user?userID={userID}", Result.class, params);
        userAddress = new User((Map) response0.getBody().getData()).getAccountAddress();
        pubkey = new User((Map) response0.getBody().getData()).getNftRSAPublicKey();
        hexPrivateKey = new User((Map) response0.getBody().getData()).getHexPrivateKey();
        System.out.println(userAddress);
    }

    @Test
    public void test() {
        String nftID = idWorker.nextId() + "";
        System.out.println(nftID);
        myFiscoClient.switchAccount(hexPrivateKey);
        myFiscoClient.mintSingle(new BigInteger(nftID), userAddress, url, "1,2,3", pubkey);
    }
}

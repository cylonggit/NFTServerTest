import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import com.market.bc.dao.NFTTransferDao;
import com.market.bc.pojo.NFTTransfer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import util.IdWorker;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class CreateTest {
    // 用于测试铸造NFT

    RestTemplate restTemplate = new RestTemplate();
    String userID;
    String url;

    @Autowired
    MyConfig myConfig;

    @Autowired
    IdWorker idWorker;

    @Autowired
    NFTTransferDao transferDao;

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


    }

    @Test
    public void test() {
        // 登记上链
        String nftID = idWorker.nextId() + "";
        System.out.println(nftID);
        Map<String, String> params = new HashMap<>();
        params.put("category", "image");
        params.put("title", "my test title");
        params.put("uri", url);
        params.put("judgeID", "my test judgeID");
        params.put("nftID", nftID);
        HttpHeaders headers = new HttpHeaders();
        headers.add("userID", userID);
        HttpEntity<Map> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/nft/nft/create", requestEntity, Map.class);

        List<NFTTransfer> list = transferDao.findNFTTransfersByNftID(nftID);
        System.out.println(list.get(0).getTransferHash());
        assertNotNull(list.get(0).getTransferHash());
        // 验证铸造合约调用成功，并返回正确的哈希值。

        Map<String, String> params1 = new HashMap<>();
        params1.put("tokenId", nftID);
        ResponseEntity<Map> response1 = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/bc/bc/getNFTInfo", params1, Map.class);
        System.out.println(response1.getBody());
        String tokenId = response1.getBody().get("tokenId").toString();
        String tokenInfo = response1.getBody().get("files").toString();
        assertEquals(nftID, tokenId);
        assertTrue(tokenInfo.contains(url));
        // 验证链上存在对应的非同质化通证
        // 验证链下存储模块存在对应的文化产品文件

        Map<String, String> params2 = new HashMap<>();
        params2.put("userID", userID);
        params2.put("nftID", nftID);
        ResponseEntity<Map> response2 = restTemplate.getForEntity(myConfig.getBackendServerUrl() + "/nft/nft/checkOwn", Map.class, params2);
        System.out.println(response2.getBody());
        assertTrue((Boolean) response2.getBody().get("flag"));
        // 验证数据库中所有权信息
    }

}

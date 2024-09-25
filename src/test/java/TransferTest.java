import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import com.market.bc.dao.NFTDao;
import com.market.bc.dao.NFTTransferDao;
import com.market.bc.fisco.FiscoBcosClient;
import com.market.bc.pojo.NFTTransfer;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class TransferTest {

    @Autowired
    MyConfig myConfig;

    @Autowired
    NFTDao nftDao;

    @Autowired
    NFTTransferDao transferDao;

    @Autowired
    FiscoBcosClient myFiscoClient;

    RestTemplate restTemplate = new RestTemplate();

    String userID1, userID2, nftID, watermark;

    @Before
    public void initUser1() { // 初始化原所有者用户
        Map<String, String> params = new HashMap<>();
        params.put("username", "mytest");
        params.put("password", "123456");
        ResponseEntity<Map> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/user/user/login", params, Map.class);
        userID1 = ((Map) response.getBody().get("data")).get("userID").toString();
        System.out.println(userID1);

        // 找到原所有者用户的一件NFT
        Map<String, String> params1 = new HashMap<>();
        params1.put("page", "1");
        params1.put("size", "1");
        params1.put("userID", userID1);
        ResponseEntity<Map> response1 = restTemplate.getForEntity(myConfig.getBackendServerUrl() + "/nft/nft/ownNFT?page={page}&size={size}&userID={userID}", Map.class, params1);
        System.out.println(response1.getBody());
        nftID = ((Map) ((List) ((Map) response1.getBody().get("data")).get("rows")).get(0)).get("nftID").toString();
        System.out.println(nftID);
        watermark = nftDao.findNFTByNftID(nftID).getNft_watermark();
        System.out.println(watermark);
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

    @Test
    public void test() throws ContractException {

        // 授权系统进行文化产品所有权转移
        Map<String, String> params = new HashMap<>();
        params.put("tokenId", nftID);
        params.put("address", userID1);
        restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/bc/bc/approve", params, Map.class);

        List<String> nftIDs = new ArrayList<>();
        nftIDs.add(nftID);

        HttpHeaders headers = new HttpHeaders();
        headers.add("userID", userID2);
        HttpEntity<List<String>> requestEntity = new HttpEntity<>(nftIDs, headers);
        restTemplate.exchange(myConfig.getBackendServerUrl() + "/nft/nft/changeOwn", HttpMethod.PUT, requestEntity, Map.class);
        // 完成文化产品的转移

        // 验证数据库层面的转移
        Map<String, String> params2 = new HashMap<>();
        params2.put("userID", userID1);
        params2.put("nftID", nftID);
        ResponseEntity<Map> response2 = restTemplate.getForEntity(myConfig.getBackendServerUrl() + "/nft/nft/checkOwn?userID={userID}&nftID={nftID}", Map.class, params2);
        System.out.println(response2.getBody());
        assertFalse((Boolean) response2.getBody().get("data"));

        params2 = new HashMap<>();
        params2.put("userID", userID2);
        params2.put("nftID", nftID);
        response2 = restTemplate.getForEntity(myConfig.getBackendServerUrl() + "/nft/nft/checkOwn?userID={userID}&nftID={nftID}", Map.class, params2);
        System.out.println(response2.getBody());
        assertTrue((Boolean) response2.getBody().get("data"));

        // 验证水印层面的转移
        String new_watermark = nftDao.findNFTByNftID(nftID).getNft_watermark();
        System.out.println(new_watermark);
        assertNotEquals(new_watermark, watermark);

        // 验证联盟链层面的转移
        List<NFTTransfer> list = transferDao.findNFTTransfersByNftID(nftID);
        NFTTransfer transfer = list.get(list.size() - 1);
        String userAddress1 = transfer.getTransferFrom();
        String userAddress2 = transfer.getTransferTo();
        String nowAddress = myFiscoClient.getContract().ownerOf(new BigInteger(nftID));
        assertNotEquals(nowAddress, userAddress1);
        assertEquals(nowAddress, userAddress2);

    }

    @After
    public void cleanUp() {
        // 把文化产品再转移回去
        Map<String, String> params = new HashMap<>();
        params.put("tokenId", nftID);
        params.put("address", userID2);
        restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/bc/bc/approve", params, Map.class);

        List<String> nftIDs = new ArrayList<>();
        nftIDs.add(nftID);

        HttpHeaders headers = new HttpHeaders();
        headers.add("userID", userID1);
        HttpEntity<List<String>> requestEntity = new HttpEntity<>(nftIDs, headers);
        restTemplate.exchange(myConfig.getBackendServerUrl() + "/nft/nft/changeOwn", HttpMethod.PUT, requestEntity, Map.class);
    }
}

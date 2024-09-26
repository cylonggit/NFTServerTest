import com.google.gson.JsonObject;
import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import com.market.bc.util.RSAUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class FileDownloadTest {
    @Autowired
    MyConfig myConfig;

    RestTemplate restTemplate = new RestTemplate();

    String userID;
    String uri;
    String nftID;
    String filename;

    @Before
    public void setup() {
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
        uri = ((Map) ((List) ((Map) response1.getBody().get("data")).get("rows")).get(0)).get("uri").toString();
        System.out.println(nftID);
        Pattern pattern = Pattern.compile("filename=([^&]+)");
        Matcher matcher = pattern.matcher(uri);
        if (matcher.find()) {
            filename = matcher.group(1);
        }
        System.out.println(filename);
    }

    @Test
    public void fileDownload() throws Exception {
        long timestamp = System.currentTimeMillis();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("timestamp", timestamp);
        jsonObject.addProperty("tokenId", nftID);
        jsonObject.addProperty("fileId", filename);
        System.out.println(jsonObject);
        String jsonString = jsonObject.toString();

        PrivateKey privateKey = RSAUtil.getPrivateKeyFromPem(Paths.get(getClass().getClassLoader().getResource("mytest-privateKey.pem").toURI()).toString());
    }
}

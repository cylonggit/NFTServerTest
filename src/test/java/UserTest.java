import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class UserTest {
    @Autowired
    MyConfig myConfig;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testLogin() {
        Map<String, String> params = new HashMap<>();
        params.put("username", "mytest");
        params.put("password", "123456");
        ResponseEntity<Map> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/user/user/login", params, Map.class);
        System.out.println(response.getBody());
        assertEquals(20000, response.getBody().get("code"));
        assertEquals("登录成功", response.getBody().get("msg"));
        assertEquals("mytest", ((Map) response.getBody().get("data")).get("username"));
        assertNotNull(((Map) response.getBody().get("data")).get("token"));
    }

    @Test
    public void testRegister() {
        Random random = new Random();
        String random12DigitNumber = String.format("%012d", random.nextInt());
        Map<String, String> params = new HashMap<>();
        params.put("username", "mytest" + random12DigitNumber);
        params.put("password", "123456");
        params.put("mobile", "131" + random12DigitNumber);
        params.put("email", "mytest" + random12DigitNumber + "@test.com");
        params.put("nftRSAPublicKey", "-----BEGIN PUBLIC KEY-----\n" +
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCy0spxQQtOzaviqyD8SPOxkUmv\n" +
                "pgOfvYzITcIBSqZZ34AqrA1BscmW5M2oba7RMWRPpYY17KzgV1QvnfeNDVl1vmBQ\n" +
                "t55EYUCUh7JwA+XF9JMb0g2zi/IrlhAqEaitZOq3En7GVRbAYVbZ4CX2F3A+EYjj\n" +
                "TdUU6hellxMb5N4pRQIDAQAB\n" +
                "-----END PUBLIC KEY-----\n");
        ResponseEntity<Map> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/user/user/register", params, Map.class);
    }

}

import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import com.market.bc.pojo.User;
import entity.Result;
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
public class UserLoginTest {
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



}

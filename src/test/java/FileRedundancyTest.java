import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import org.junit.After;
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

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class FileRedundancyTest {
    @Autowired
    MyConfig myConfig;

    RestTemplate restTemplate = new RestTemplate();

    String url;

    @Before
    public void init() throws URISyntaxException {
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        Path path = Paths.get(getClass().getClassLoader().getResource("images/2.jpg").toURI());
        bodyMap.add("file", new FileSystemResource(path.toFile()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<Map> response1 = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/file/file/upload", requestEntity, Map.class);

        url = response1.getBody().get("url").toString().replace("http://127.0.0.1:9015", myConfig.getBackendServerUrl());
        System.out.println(url);
    }

    @Test
    public void testFileRedundancy() {
        assertTrue(isUrlReachable(url));
    }

    @After
    public void cleanup() {

    }

    public static boolean isUrlReachable(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

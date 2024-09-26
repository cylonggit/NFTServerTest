import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import entity.Result;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class ImportTest {
    @Autowired
    MyConfig myConfig;

    RestTemplate restTemplate = new RestTemplate();


    @Test
    public void test() throws URISyntaxException {
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        Path path = Paths.get(getClass().getClassLoader().getResource("picture.zip").toURI());
        bodyMap.add("file", new FileSystemResource(path.toFile()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<Result> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/nft/nft/uploadFiles", requestEntity, Result.class);


        assertTrue(response.getBody().isFlag());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(20000, response.getBody().getCode().intValue());
        System.out.println(response.getBody().getMsg());
    }
}

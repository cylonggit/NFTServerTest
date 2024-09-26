import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class WatermarkTest {
    @Autowired
    MyConfig myConfig;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void test() throws URISyntaxException {
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        Path path = Paths.get(getClass().getClassLoader().getResource("images/2.jpg").toURI());
        bodyMap.add("file", new FileSystemResource(path.toFile()));
        bodyMap.add("message", "111100000000000000000011111011");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<ByteArrayResource> response1 = restTemplate.postForEntity(myConfig.getWaterServerUrl(), requestEntity, ByteArrayResource.class);

        assertEquals(200, response1.getStatusCodeValue());

        boolean flag = false;
        // 检查响应状态码
        if (response1.getStatusCode().is2xxSuccessful()) {
            ByteArrayResource resource = response1.getBody();
            if (resource != null) {
                // 指定文件保存路径
                String savePath = Paths.get(getClass().getClassLoader().getResource("images/3.jpg").toURI()).toString();

                // 保存文件
                try (OutputStream outputStream = new FileOutputStream(savePath)) {
                    byte[] data = resource.getByteArray();
                    outputStream.write(data);
                    System.out.println("File saved successfully at: " + savePath);
                    flag = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("Failed to retrieve the file from the server");
        }

        assertTrue(flag);

        MultiValueMap<String, Object> bodyMap1 = new LinkedMultiValueMap<>();
        Path path1 = Paths.get(getClass().getClassLoader().getResource("images/3.jpg").toURI());
        bodyMap1.add("file", new FileSystemResource(path1.toFile()));
        HttpEntity<MultiValueMap<String, Object>> requestEntity1 = new HttpEntity<>(bodyMap1, headers);
        ResponseEntity<Map> response2 = restTemplate.postForEntity(myConfig.getWaterServerUrl().replace("/embed","/extract"), requestEntity1, Map.class);

        assertEquals(200, response2.getStatusCodeValue());

        System.out.println(response2.getBody());
        String message = response2.getBody().get("message").toString().replaceAll("\\D", "");
        System.out.println(message);
        assertEquals("111100000000000000000011111011", message);
    }
}

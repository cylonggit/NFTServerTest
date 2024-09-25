
import com.market.bc.TestApplication;
import com.market.bc.configurer.MyConfig;
import com.market.bc.pojo.NFTTransfer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= TestApplication.class) // 指定spring-boot的启动类
public class TestExample {
    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    MyConfig myConfig;
    public static final Logger log = LoggerFactory.getLogger(TestExample.class.getName());
//    @Autowired
//    FiscoBcosClient myFiscoClient;

    @Before //为了测试预务好链上、mysql、minio、文件等数据
    public void init(){
        System.out.println("...int the env before the test....");
    }

    //@Ignore
    @Test //测试功能，并与预期结果进行对比后并断言，断言使用asset
    public void testSomething() {
        System.out.println("...do the test here, including the assertion...");


        Map<String, String> params = new HashMap<>();
        params.put("num1", "2");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(myConfig.getBackendServerUrl() + "/nft/nft/test1", requestEntity, Map.class);
        System.out.println(response);



        assert (1==1);
   }

    //@Test
    public void smsTest() {
        //4.发送post请求
        String url = myConfig.getBackendServerUrl() + "/nft/nft/test1";
        String appCode = "APPCODE 65ed5499417944f1ad101a12014a80c9";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = uriComponentsBuilder.build().encode(StandardCharsets.UTF_8).toUri();

        //1.请求头
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", appCode);
        httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("num1", "111");
        requestMap.add("num2", "填写真正的手机号码");
        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(httpHeaders)
                .body(requestMap);

        ResponseEntity<Result> responseEntity;
        try {
            responseEntity = restTemplate.exchange(requestEntity, Result.class);
            //log.info(Objects.requireNonNull(responseEntity.getBody()).toString());
            log.info(responseEntity.getBody().toString());
        } catch (RestClientException e) {
            log.error("[RestTemplateTest-test] http request error", e);
        }

    }

   @After  //主要是清理一些为了测试而弄出来的东西，如链上的NFT烧掉、mysql中的东西删除，或Minio的图片删掉等
    public void afterTest(){
       System.out.println("...if need to do something after the test, you can do here....");
    }

}




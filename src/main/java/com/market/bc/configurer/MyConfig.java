package com.market.bc.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// todo 这里提供获取application.yml中配置值的参考代码，请把所有的写死配置在代码的全写到配置文件application中
@Component
public class MyConfig {
    @Value("${waterServer.url}")
    private String waterServer;

    public String getWaterServerUrl() {
        return waterServer;
    }

}

package com.market.bc.fisco;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "fisco")
public class FiscoProp {
    private String defaultAccoutPath;
    private Integer defaultGroup;
    private String password;
    private String privateKey;
    private String publicKey;
    private String configFile;
    private String bucket;

    public String getDefaultAccoutPath() {
        return defaultAccoutPath;
    }

    public void setDefaultAccoutPath(String defaultAccoutPath) {
        this.defaultAccoutPath = defaultAccoutPath;
    }

    public Integer getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(Integer defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
package com.market.bc.fisco;
import com.google.gson.Gson;
import com.market.bc.contract.Mynft;
import com.market.bc.pojo.*;
import com.market.bc.util.Base64;
import com.market.bc.util.RSAEncrypt;
import com.market.bc.util.RsaKeyUtils;
import entity.Result;
import entity.StatusCode;
import lombok.Data;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.wrapper.ABIObject;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.ECDSAKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderService;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Hash;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;
import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

@Component
@Data
public class FiscoBcosClient {
    @Autowired
    FiscoProp fiscoProp;
    private ConfigProperty configProperty;
    private BcosSDK bcosSDK;
    private Client client;
    private CryptoKeyPair cryptoKeyPair;
    private Mynft contract;
//    private AssembleTransactionProcessor transactionProcessor;


    @PostConstruct  //相当于自动运行的初始化
    public void initialize() throws Exception {
        //通过.yml配置生成BcosSDK
        configProperty = loadProperty();
        ConfigOption configOption ;
        try {
            configOption = new ConfigOption(configProperty, CryptoType.ECDSA_TYPE);
        } catch (ConfigException e) {
            System.out.println("phase fisco config error:" + e.toString());
            return ;
        }
        bcosSDK = new BcosSDK(configOption);
        //通过.toml配置文件生成BcosSDK
//        String configFile=this.getClass().getClassLoader().getResource("fisco-config.toml").getPath();
//        System.out.println("++++++++++++++++++++configFile:"+configFile);
//        bcosSDK =BcosSDK.build(configFile);
        // 初始化可向群组fiscoProp.getDefaultGroup()发交易的Client
        client = bcosSDK.getClient(fiscoProp.getDefaultGroup());
//        // 随机生成发送交易的公私钥对
//        cryptoKeyPair = client.getCryptoSuite().createKeyPair();
//        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
        //加载账户
        // 通过client获取CryptoSuite对象
        //CryptoSuite cryptoSuite = client.getCryptoSuite();
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
//        // 加载pem账户文件
//        String pemAccountFilePath=this.getClass().getClassLoader().getResource(fiscoProp.getDefaultAccoutPath()).getPath();
//        System.out.println("++++++++++++++++++++path:"+pemAccountFilePath);
//        cryptoSuite.loadAccount("pem", pemAccountFilePath, null);
        CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair();  //随机生成了一个区块链账号对
//        cryptoKeyPair=client.getCryptoSuite().getCryptoKeyPair();

        String contractAddress = loadMynftAddr();
        contract = Mynft.load(contractAddress, client, cryptoKeyPair);
        System.out.println("client for group1, account address is " + cryptoKeyPair.getAddress());
//        transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, cryptoKeyPair, "src/main/resources/abi/", "src/main/resources/bin/");
    }

    public String deployMynftAndRecordAddr() {
        try {
            String contractName="Mynft";
            String contractSymbol="SNFT";
            Mynft mynft = Mynft.deploy(client, cryptoKeyPair,contractName,contractSymbol);
            System.out.println(
                    " deploy Mynft success, contract address is " + mynft.getContractAddress());

            recordMynftAddr(mynft.getContractAddress());
            return mynft.getContractAddress();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(" deploy Mynft contract failed, error message is  " + e.getMessage());
            return null;
        }
    }

    public void recordMynftAddr(String address) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("address", address);
        final Resource contractResource = new ClassPathResource("contract.properties");
        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
        prop.store(fileOutputStream, "contract address");
    }

    public String loadMynftAddr() throws Exception {
        // load Mynft contact address from contract.properties
        Properties prop = new Properties();
        final Resource contractResource = new ClassPathResource("contract.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress = prop.getProperty("address");
        if (contractAddress == null || contractAddress.trim().equals("")) {
            System.out.println(" load Mynft contract address failed, now try deploy it first. ");
            String addr=  deployMynftAndRecordAddr();
            return addr;
        }
        System.out.println(" load Mynft address from contract.properties, address is "+ contractAddress);
        return contractAddress;
    }

    public String getContractAddress() {
        return contract.getContractAddress();
    }

    public ConfigProperty loadProperty() {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(representer);
        String configFile = fiscoProp.getConfigFile();
        try (InputStream inputStream = this.getClass().getResourceAsStream(configFile)) {
            return yaml.loadAs(inputStream, ConfigProperty.class);
        } catch (Exception e) {
            System.out.println("load property: "+e.toString());
            return null;
        }
    }



    // 公私钥验证方法 参考
    public Result isSignValid(String tokenId, String fileId, long timestamp, String cipher) throws Exception {  //(tokenId,fileId,timestamp, cipher)
        FileRequestArg signData = new FileRequestArg(timestamp,tokenId,fileId);

        String pubkey = null;
        try {
            pubkey = contract.publicKey(new BigInteger(tokenId));
        } catch (Exception e) {
            return new Result(true, StatusCode.NOPUBKEY, "can't get the public key from blockchain");
        }

        RSAPublicKey  pubKeyOject=null;
        try {
            pubKeyOject= RsaKeyUtils.loadPublicKeyByStr(pubkey);
        } catch (Exception e) {
            return new Result(true, StatusCode.NOPUBKEY, "the public key from blockchain is wrong");
        }
        Gson gson = new Gson();
        String signDataStr=gson.toJson(signData);
        System.out.println("signDataStr: "+signDataStr);
        CipherArg cipherArg=gson.fromJson(cipher,CipherArg.class);
        System.out.println("cipher: "+cipherArg.getCipher());


//        String priKeyStr= "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALxGCs3hFp6n8n2e6/JcB9/n02hz53NdoaWLPabfQzO0LaGkC2G8wGLE9ae8fRhT4g9Si/1jvPi3/pXo+mduYgrCtY4StZFpltDsrlFeAx5pcBZDoAsgP81WSfxOhKYxOvBSn0kdZS5xSiBHoA35vFC6wuBbvEW3B7GWd4y/vgQNAgMBAAECfwe417iaAtN/D/MXEZZfL/N/Dqy6FEQnEvo3O9AcWCVGvphiohTgKQPkEC9RC9BzoFIAkL1T2exZn5eGdr7HSD4Wjm1zmtFg7LeoIOqGOXeqZlJ9tS9elAX/foDS1zBVeY30KEqUSBIdcy5UGJ4Rj6whWIeZAYILl3/54VIkbYECQQD8qHoIR8TECLJRw1EMsm8KbUOpqgqppuo7RifLSTRrE4BpsR8l1WZzqKym2TsYCakLObrWDhgrO5LG0EUTvrVBAkEAvsONwH+cSk7Tehn8lwe+Pa3JEyJ4fF6L6dMJt81thTZZRUm2e+F1LeNgpjXbDhp+ciO5L3rW9xQpWKw67xQfzQJBAMn9PyJjjhFTCoyHY8KM3G0vkUqQ4Nlk0hn1KldA/SElhCH7MnWit7d7gBwbyzyabITG8HG07umRRPShCLiSXIECQGZZUUKItPgBVuFZ8/WsR9ub1BgaFH7V00d+3OXqp+ojAL52EBxjGJWoOwCyQHdOq6zdkcvxokgqXwyeS3heWbkCQQCku2S2dje5E0AaoIbuBh/acV0diSjJdDSmpmJuyI6xFcuOMC+iMwIvhFEtVI05yRAA8FZmzwconjEF4NhosfDp";
//        String priKeyStr="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ+GtJ9QsWAtuWZFYhMOFeerk1TcsQHm0QebRpdhI/kPUIN5BJ7SjMEXlwxiOgI3c26kFjUvnjctKwqTWAOEK6eRllJc+UdJ08PjznVX+ppDd7tGqvaaOkkhjcLCQgkrVQZlkstsAtIIljMPLi0y7/5ccFK6xNw91CaVhLGcVKslAgMBAAECgYAjudI3n6WpqXPM4Jr/M3daY8ViCu6lQ2DquC6N9lZND83zM6YCP5sAH11Xi6P8HRj/3UWP8bMYHkh2XNsoJnB0Avf+675U6KinfJ/0lo6OWeL8DiAmiv9vmKK2641t+wQ/XTSsLxRkPzoP1h5IqdpAkyLzltqhV9LCwa1hFl2knQJBANSZtHG76OI+REUkpCSyRg+vuQf0E9JCZCCtnyxVvameaaOsd3DIn6D9AVnwIN7pVsJVFMaQg3OhsyDy/b7X6mcCQQDAF2SFX16qmtDcZraOm5ChyKNApZs6+6JfbLJLJFhWHwPZxwwbK+fu0qXaUpBmC5nQ2TXdNjWwJ2rZQsxFdx6TAkEAkXuJFlc/Wyq+7w3O4Wm5NdzK4p1svvI9sHr/NIiL3DmSErXfmic/zRu1RAVliQTmIN+18tdLjOI3QTHm4CTCuwJBALcUFIxBTzqO/01w38GfZwOq9F1i+UYIGsOUF4Iii8bZHJRtb45eFFm8jrI8Sr/XgqxYuUuypQzVJLMZRRNX6t0CQCAR6VFWqA4/FwW9wScBo4k7OaXWXRciZ0jKHQXpOdjvTfXCZJUDBaADO/PlVl3pMO/vMJUhxpmrjD2GfwgYI4Q=";
//        String str ="{\"timestamp\":1725893970701,\"tokenId\":\"1833157098821783552\",\"fileId\":\"ce6e5411-460a-4d56-bf97-e4673a7680d1.png\"}";
//        System.out.println("cipherJson: "+cipher);
//        RSAPrivateKey priKey =RsaKeyUtils.loadPrivateKeyByStr(priKeyStr);
//        byte[] signautreResult = RsaKeyUtils.sign(str, priKey.getEncoded());
//        String signatureStr = RsaKeyUtils.bytes2String(signautreResult);
//        System.out.println("signStr: "+signatureStr);
//        byte[] signatureResult2 = RsaKeyUtils.hexStringToByteArray(signatureStr);
//        boolean c = RsaKeyUtils.verify(str, pubKeyOject.getEncoded(), signatureResult2);
//        System.out.print("iii   " + c);
//
//        System.out.println("now compare the plaintext:");
//        System.out.println(str);
//        System.out.println(signDataStr);
//        boolean areStringsEqual = str.equals(signDataStr);
//        System.out.println(areStringsEqual);

        byte[] signatureResult=RsaKeyUtils.hexStringToByteArray(cipherArg.getCipher());
        boolean b = RsaKeyUtils.verify(signDataStr, pubKeyOject.getEncoded(), signatureResult);
        System.out.print("sign verify result:  " + b);
        if(b){
            if (signData.isValid(10)) {
                return new Result(true, StatusCode.OK, "success");
            } else {
                return new Result(true, StatusCode.ERROR, "the token in cipher is out of time");
            }
        }else {
            return new Result(true, StatusCode.ERROR, "the token in cipher is out of time");
        }





    }

    public Result isFilePublic(String tokenId, String fileId) {
        Tuple2<String, String> meta = null;
        try {
            meta = contract.tokenURI(new BigInteger(tokenId));
            String filesStr=meta.getValue1();
            String auto=meta.getValue2();
            Gson gson=new Gson();
            NFTFiles files= gson.fromJson(filesStr, NFTFiles.class);
            int i=0;
            for(int j=0;j<files.getFiles().size();j++){
                if(files.getFiles().get(j).getFileId().equals(fileId)) {
                    i = j;
                    break;
                }
            }
            if(auto.charAt(i)=='0'){
                return new Result(true, StatusCode.OK, "success");
            }else {
                return new Result(false, StatusCode.OK, "success");
            }
        } catch (Exception e) {
            return new Result(false, StatusCode.ERROR, e.getMessage());
        }
    }

    public String getPublicKey(BigInteger tokenId) throws ContractException {
        return contract.publicKey(tokenId);
    }
    public TransactionReceipt setPublicKey(BigInteger tokenId, String pubKey) {
        return contract.setPublicKey(tokenId, pubKey);
    }

    //获取某个NFT的水印信息的哈希
    public byte[] getWatermarkHash(BigInteger tokenId) throws ContractException {
        return contract.watermarkHash(tokenId);
    }
    //设置某个NFT的水印信息的哈希
    public TransactionReceipt setWatermarkHash(BigInteger tokenId, byte[] watermarkHash) {
        return contract.setWatermarkHash(tokenId, watermarkHash);
    }

    //owner对某个NFT进行一次转移授权
    public TransactionReceipt approvalForOnce(BigInteger tokenId, String to) {
        return contract.approve(to,tokenId);
    }
    //查询某个NFT的一次转移授权情况
    public String getApprovale(BigInteger tokenId) throws ContractException {
        return contract.getApproved(tokenId);
    }
    //owner对清除NFT进行一次转移授权
    public TransactionReceipt clearApproval(BigInteger tokenId) {
        return contract.clearApproval(tokenId);
    }
    //获取NFT合约的生成者，也是平台账号
    public String getPlatformAddress() throws ContractException {
        return contract.creator();
    }

//    struct Meta {
//        string files;   //约定为[{"uri":"xxxx","bashHash":"xxxx"},{"uri":"xxxx","baseHash":"xxxx"}]   files现在扩展为： 【uri,baseHash,phash,dhash,】 其中phash为感知哈希,dhash为{作者证件号、发行商id、权利登记号，时间}的json字符串的哈希值。
//        string auth;  //约定为“10”这种字符串形式作为权限标志，表示uris中相应的uri的访问权限，1为需要鉴权，0为公开. 如果所存的位数比文件数多，则只取前面的位。如果比文件数少，则缺少标志的默认为1
//    }
    public TransactionReceipt mintSingle(BigInteger tokenId, String to, String uri, String baseHash, String pubkey) {
        String[] str = baseHash.split(",");
        String file = String.format("{\"uri\":\"%s\", \"bashHash\":\"%s\", \"pHash\":\"%s\", \"dHash\":\"%s\", \"fileId\":\"%s_001\"}"
                , uri, str[0], str[1], str[2], tokenId.toString());

        return contract.mintWithPubkey(tokenId, to, file, "0", pubkey);
}
    public TransactionReceipt mint(BigInteger tokenId, String to, String files, String auth) {
        return contract.mint(tokenId, to, files, auth);
    }

    public TransactionReceipt mintWithPubkey(BigInteger tokenId, String to, String files, String auth, String pubkey) {
        return contract.mintWithPubkey(tokenId, to, files, auth, pubkey);
    }

//    public TransactionReceipt safeTransferFrom(String from, String to, BigInteger tokenId, String pubKey) {
//        return safeTransferFrom(from, to, tokenId, pubKey);
//    }

    //NFT转移，每次转后会自动清掉一次性授权
    public TransactionReceipt safeTransferFrom(String from, String to, BigInteger tokenId, String pubKey) {
        return contract.safeTransferFrom(from, to, tokenId, pubKey);
    }
    //NFT转移，每次转后会自动清掉一次性授权
    public TransactionReceipt safeTransferFrom(String from, String to, BigInteger tokenId, byte[] data, String pubKey){
        return contract.safeTransferFrom( from,  to,  tokenId,  data,  pubKey);
    }

    public String switchAccount(String hexPrivateKey) {  //把clent改成指定账号
        ECDSAKeyPair keyFacotry = new ECDSAKeyPair();
        CryptoKeyPair cryptoKeyPair = keyFacotry.createKeyPair(hexPrivateKey);
        String contractAddress = getContractAddress();
        contract = Mynft.load(contractAddress, client, cryptoKeyPair);
        System.out.println("client for group1, account address is " + cryptoKeyPair.getAddress());
        return cryptoKeyPair.getAddress();
    }

    public String getAccountAddress(String hexPrivateKey) {
        ECDSAKeyPair keyFacotry = new ECDSAKeyPair();
        CryptoKeyPair cryptoKeyPair = keyFacotry.createKeyPair(hexPrivateKey);
        return cryptoKeyPair.getAddress();
    }

    public void switchDefaultAccount() {
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        // 加载pem账户文件
        String pemAccountFilePath=this.getClass().getClassLoader().getResource(fiscoProp.getDefaultAccoutPath()).getPath();
        System.out.println("++++++++++++++++++++path:"+pemAccountFilePath);
        cryptoSuite.loadAccount("pem", pemAccountFilePath, null);
        CryptoKeyPair cryptoKeyPair=client.getCryptoSuite().getCryptoKeyPair();

        String contractAddress = getContractAddress();
        contract = Mynft.load(contractAddress, client, cryptoKeyPair);
        System.out.println("client for group1, account address is " + cryptoKeyPair.getAddress());
    }

    // 根据交易哈希获取 Mint交易信息的接口
    public MintParameter getMintTransactionByHash(String transactionHash) throws ABICodecException, TransactionException, IOException {
        TransactionReceipt re=client.getTransactionReceipt(transactionHash).getResult();
        System.out.println(re.getTransactionIndex());

        // 获取当前群组对应的密码学接口
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        // 构造TransactionDecoderService实例，传入是否密钥类型参数。
        TransactionDecoderInterface decoder = new TransactionDecoderService(cryptoSuite);
        TransactionResponse transactionResponse = decoder.decodeReceiptWithValues(contract.ABI, "mintWithPubkey", re);

        List<ABIObject> inputs = transactionResponse.getInputABIObject();
        MintParameter mp=new MintParameter();
        mp.setReceipt(re);
        mp.setTokenId(inputs.get(0).getNumericValue().getValue());
        mp.setTo(inputs.get(1).getAddressValue().getValue());
        mp.setFiles(inputs.get(2).getStringValue().getValue());
        mp.setAuth(inputs.get(3).getStringValue().getValue());
        mp.setPubkey(inputs.get(4).getStringValue().getValue());
        return mp;
    }
    // 根据交易哈希获取 SafeTransfer交易信息的接口
    public TransferParameter getSafeTransferTransactionByHash(String transactionHash) throws ABICodecException, TransactionException, IOException {
        TransactionReceipt re=client.getTransactionReceipt(transactionHash).getResult();
        System.out.println(re.getTransactionIndex());

        // 获取当前群组对应的密码学接口
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        // 构造TransactionDecoderService实例，传入是否密钥类型参数。
        TransactionDecoderInterface decoder = new TransactionDecoderService(cryptoSuite);
        TransactionResponse transactionResponse = decoder.decodeReceiptWithValues(contract.ABI, "safeTransferFrom", re);

        List<ABIObject> inputs = transactionResponse.getInputABIObject();
        TransferParameter mp=new TransferParameter();
        mp.setReceipt(re);
        mp.setFrom(inputs.get(0).getAddressValue().getValue());
        mp.setTo(inputs.get(1).getAddressValue().getValue());
        mp.setTokenId(inputs.get(2).getNumericValue().getValue());
        mp.setPubkey(inputs.get(3).getStringValue().getValue());
        return mp;
    }

    public static byte[] getHashBytesFromString(String plaintext){
        byte[] hash =  Hash.sha3(plaintext.getBytes());
        return hash;
    }



}

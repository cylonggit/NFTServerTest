//package com.market.minio.fisco;
//
//import com.market.minio.contract.Mynft;
//import org.fisco.bcos.channel.client.PEMManager;
//import org.fisco.bcos.channel.client.Service;
//import org.fisco.bcos.web3j.crypto.Credentials;
//import org.fisco.bcos.web3j.crypto.ECKeyPair;
//import org.fisco.bcos.web3j.crypto.gm.GenCredential;
//import org.fisco.bcos.web3j.protocol.Web3j;
//import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
//import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.web3j.protocol.core.methods.response.TransactionReceipt;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.UnrecoverableKeyException;
//import java.security.cert.CertificateException;
//import java.security.spec.InvalidKeySpecException;
//
//public class Web3Client {
//    @Autowired
//    FiscoProp fiscoProp;
//    PEMManager pem=new PEMManager();
//    public void init() throws CertificateException, InvalidKeySpecException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, IOException, UnrecoverableKeyException {
//        String pemAccountFilePath=this.getClass().getClassLoader().getResource(fiscoProp.getDefaultAccoutPath()).getPath();
//        pem.setPemFile(pemAccountFilePath);
//        pem.load();
//        ECKeyPair pemKeyPair = pem.getECKeyPair();
//
////以十六进制串输出私钥和公钥
//        System.out.println("PEM privateKey: " + pemKeyPair.getPrivateKey().toString(16));
//        System.out.println("PEM publicKey: " + pemKeyPair.getPublicKey().toString(16));
////生成Web3SDK使用的Credentials
//        Credentials credentialsPEM = GenCredential.create(pemKeyPair.getPrivateKey().toString(16));
//        System.out.println("PEM Address: " + credentialsPEM.getAddress());
//
//        //读取配置文件，SDK与区块链节点建立连接，获取web3j对象
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        Service service = context.getBean(Service.class);
//        service.run();
//        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
//        channelEthereumService.setChannelService(service);
//        channelEthereumService.setTimeout(10000);
//        Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());
//        //准备部署和调用合约的参数
//        BigInteger gasPrice = new BigInteger("300000000");
//        BigInteger gasLimit = new BigInteger("300000000");
//        String privateKey = pemKeyPair.getPrivateKey().toString(16);
//        //指定外部账户私钥，用于交易签名
//        Credentials credentials = GenCredential.create(privateKey);
//        //部署合约
//        Mynft contract = Mynft.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
//        //根据合约地址加载合约
//        //YourSmartContract contract = YourSmartContract.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
//        //调用合约方法发送交易
////        TransactionReceipt transactionReceipt = contract.someMethod(<param1>, ...).send();
////        //查询合约方法查询该合约的数据状态
////        Type result = contract.someMethod(<param1>, ...).send();
//
//    }
//}

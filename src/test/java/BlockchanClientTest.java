import com.market.bc.TestApplication;
import com.market.bc.fisco.FiscoBcosClient;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class BlockchanClientTest {
    public static final Logger logger = LoggerFactory.getLogger(BlockchanClientTest.class.getName());

    @Autowired
    FiscoBcosClient myFiscoClient;


    // 测试当前使用的区块链账户是否正确切换
    @Test
    public void switchAccountAddressTest() {
        // 随机生成一个新区块链账户
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair();
        // 获取生成的区块链账户地址
        String address = cryptoKeyPair.getAddress();
        String hexPrivateKey = cryptoKeyPair.getHexPrivateKey();

        // 切换账户
        myFiscoClient.switchAccount(hexPrivateKey);
        // 获取区块链当前账户地址
        String nowAddress = myFiscoClient.getContract().getCurrentExternalAccountAddress();
        assertEquals(address, nowAddress);
    }
}

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
public class CreatorAddressTest {
    public static final Logger logger = LoggerFactory.getLogger(CreatorAddressTest.class.getName());

    @Autowired
    FiscoBcosClient myFiscoClient;



    // 测试合约的生成者，也就是平台的区块链账户地址
    @Test
    public void platformAddressTest() throws ContractException {
        String platformAddress = myFiscoClient.getPlatformAddress();
        assertEquals("0x5d0a90ad4af83cacb78c9ba0596bd2fd2b94d68d", platformAddress);
    }


}

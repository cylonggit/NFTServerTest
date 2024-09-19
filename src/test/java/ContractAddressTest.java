import com.market.bc.TestApplication;
import com.market.bc.fisco.FiscoBcosClient;
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
public class ContractAddressTest {
    public static final Logger logger = LoggerFactory.getLogger(ContractAddressTest.class.getName());

    @Autowired
    FiscoBcosClient myFiscoClient;

    @Test
    public void fiscoAddressTest() {
        String contractAddress = myFiscoClient.getContractAddress();
        assertEquals("0xcffa8ebe237724a1714e64453693e2040b2b25cd", contractAddress);
    }
}

package ltd.beihu.leaf.server.service;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import ltd.beihu.leaf.beihuleaffacade.SnowflakeService;
import ltd.beihu.leaf.core.IDGen;
import ltd.beihu.leaf.core.common.PropertyFactory;
import ltd.beihu.leaf.core.common.ZeroIDGen;
import ltd.beihu.leaf.core.snowflake.SnowflakeIDGenImpl;
import ltd.beihu.leaf.server.common.Constants;
import ltd.beihu.leaf.server.exception.InitException;
import ltd.beihu.leaf.server.utils.LeafUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@SofaService(interfaceType = SnowflakeService.class, uniqueId = "${beihu.rpc.beihu-leaf}",
        bindings = { @SofaServiceBinding(
                bindingType = "${beihu.rpc.binding-type}")
        })
public class SnowflakeServiceImpl implements SnowflakeService {

    private static Logger logger = LoggerFactory.getLogger(SnowflakeServiceImpl.class);

    private IDGen idGen;

    public SnowflakeServiceImpl() throws InitException {
        Properties properties = PropertyFactory.getProperties();
        boolean flag = Boolean.parseBoolean(properties.getProperty(Constants.LEAF_SNOWFLAKE_ENABLE, "true"));
        if (flag) {
            String zkAddress = properties.getProperty(Constants.LEAF_SNOWFLAKE_ZK_ADDRESS);
            int port = Integer.parseInt(properties.getProperty(Constants.LEAF_SNOWFLAKE_PORT));
            idGen = new SnowflakeIDGenImpl(zkAddress, port);
            if(idGen.init()) {
                logger.info("Snowflake Service Init Successfully");
            } else {
                throw new InitException("Snowflake Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            logger.info("Zero ID Gen Service Init Successfully");
        }
    }

    @Override
    public String getId(String key) {
        return LeafUtils.get(key, idGen.get(key));
    }
}

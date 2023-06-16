package com.nabob.conch.leaf.server.service;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.nabob.conch.leaf.facade.SnowflakeService;
import com.nabob.conch.leaf.core.IDGen;
import com.nabob.conch.leaf.core.common.PropertyFactory;
import com.nabob.conch.leaf.core.common.ZeroIDGen;
import com.nabob.conch.leaf.core.snowflake.SnowflakeIDGenImpl;
import com.nabob.conch.leaf.server.common.Constants;
import com.nabob.conch.leaf.server.exception.InitException;
import com.nabob.conch.leaf.server.utils.LeafUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@SofaService(interfaceType = SnowflakeService.class, uniqueId = "${conch.rpc.conch-leaf}",
        bindings = { @SofaServiceBinding(
                bindingType = "${conch.rpc.binding-type}")
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

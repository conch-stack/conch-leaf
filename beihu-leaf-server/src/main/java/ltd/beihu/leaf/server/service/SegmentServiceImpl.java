package ltd.beihu.leaf.server.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import ltd.beihu.leaf.beihuleaffacade.SegmentService;
import ltd.beihu.leaf.core.IDGen;
import ltd.beihu.leaf.core.common.PropertyFactory;
import ltd.beihu.leaf.core.common.ZeroIDGen;
import ltd.beihu.leaf.core.segment.SegmentIDGenImpl;
import ltd.beihu.leaf.core.segment.dao.IDAllocDao;
import ltd.beihu.leaf.core.segment.dao.impl.IDAllocDaoImpl;
import ltd.beihu.leaf.server.common.Constants;
import ltd.beihu.leaf.server.exception.InitException;
import ltd.beihu.leaf.server.utils.LeafUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Properties;

@Service
@SofaService(interfaceType = SegmentService.class, uniqueId = "${beihu.rpc.beihu-leaf}",
        bindings = { @SofaServiceBinding(
                bindingType = "${beihu.rpc.binding-type}")
        })
public class SegmentServiceImpl implements SegmentService {

    private static Logger logger = LoggerFactory.getLogger(SegmentServiceImpl.class);

    private IDGen idGen;
    private DruidDataSource dataSource;

    public SegmentServiceImpl() throws SQLException, InitException {
        Properties properties = PropertyFactory.getProperties();
        boolean flag = Boolean.parseBoolean(properties.getProperty(Constants.LEAF_SEGMENT_ENABLE, "true"));
        if (flag) {
            // Config dataSource
            dataSource = new DruidDataSource();
            dataSource.setUrl(properties.getProperty(Constants.LEAF_JDBC_URL));
            dataSource.setUsername(properties.getProperty(Constants.LEAF_JDBC_USERNAME));
            dataSource.setPassword(properties.getProperty(Constants.LEAF_JDBC_PASSWORD));
            dataSource.init();

            // Config Dao
            IDAllocDao dao = new IDAllocDaoImpl(dataSource);

            // Config ID Gen
            idGen = new SegmentIDGenImpl();
            ((SegmentIDGenImpl) idGen).setDao(dao);
            if (idGen.init()) {
                logger.info("Segment Service Init Successfully");
            } else {
                throw new InitException("Segment Service Init Fail");
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

    public SegmentIDGenImpl getIdGen() {
        if (idGen instanceof SegmentIDGenImpl) {
            return (SegmentIDGenImpl) idGen;
        }
        return null;
    }
}

package ltd.beihu.leaf.server.utils;

import ltd.beihu.leaf.core.common.Result;
import ltd.beihu.leaf.core.common.Status;
import ltd.beihu.leaf.server.exception.LeafServerException;
import ltd.beihu.leaf.server.exception.NoKeyException;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: zjz
 * @Desc:
 * @Date: 2019/3/10
 * @Version: V1.0.0
 */
public class LeafUtils {

    public static String get(@PathVariable("key") String key, Result id) {
        Result result;
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }

        result = id;
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(result.toString());
        }
        return String.valueOf(result.getId());
    }
}

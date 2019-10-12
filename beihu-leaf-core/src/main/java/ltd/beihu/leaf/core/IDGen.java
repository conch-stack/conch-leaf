package ltd.beihu.leaf.core;

import ltd.beihu.leaf.core.common.Result;

public interface IDGen {

    Result get(String key);

    boolean init();
}

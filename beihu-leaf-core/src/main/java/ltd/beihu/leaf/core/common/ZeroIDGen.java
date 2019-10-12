package ltd.beihu.leaf.core.common;

import ltd.beihu.leaf.core.IDGen;

public class ZeroIDGen implements IDGen {

    @Override
    public Result get(String key) {
        return new Result(0, Status.SUCCESS);
    }

    @Override
    public boolean init() {
        return true;
    }
}

package com.nabob.conch.leaf.core.common;

import com.nabob.conch.leaf.core.IDGen;

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

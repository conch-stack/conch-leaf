package com.nabob.conch.leaf.core;

import com.nabob.conch.leaf.core.common.Result;

public interface IDGen {

    Result get(String key);

    boolean init();
}

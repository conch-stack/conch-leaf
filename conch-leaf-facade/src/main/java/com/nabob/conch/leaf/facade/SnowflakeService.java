package com.nabob.conch.leaf.facade;

/**
 * @Author: zjz
 * @Desc: RPC Snowflake ID Interface
 * @Date: 2019/10/12
 * @Version: V1.0.0
 */
public interface SnowflakeService {

    String getId(String key);
}

package com.nabob.conch.leaf.server.controller;

import com.nabob.conch.leaf.server.service.SegmentServiceImpl;
import com.nabob.conch.leaf.server.service.SnowflakeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zjz
 * @Desc: Distributed ID for Http
 * @Date: 2019/3/10
 * @Version: V1.0.0
 */
@RestController
@RequestMapping("/api")
public class LeafController {

    @Autowired
    private SegmentServiceImpl segmentService;
    @Autowired
    private SnowflakeServiceImpl snowflakeService;

    @RequestMapping(value = "/segment/get/{key}")
    public String getSegmentId(@PathVariable("key") String key) {
        return segmentService.getId(key);
    }

    @RequestMapping(value = "/snowflake/get/{key}")
    public String getSnowflakeId(@PathVariable("key") String key) {
        return snowflakeService.getId(key);
    }
}

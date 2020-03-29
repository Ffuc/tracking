package com.ruixun.tracking.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-29 13:05
 **/

@CrossOrigin
@RestController
@RequestMapping("/test")
@Api("跨域测试controller")
public class TestController {

    @PostMapping("/test")
    public String hello(@RequestBody String name) {
        System.out.println(name);
        return name;

    }

}

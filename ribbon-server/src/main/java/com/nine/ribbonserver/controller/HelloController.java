package com.nine.ribbonserver.controller;

import com.nine.ribbonserver.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： 月在未央
 * @date： 2018/11/29 14:02
 * @Description：
 */
@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @GetMapping
    public String hi(@RequestParam String name){
        return helloService.hiService(name);
    }
}

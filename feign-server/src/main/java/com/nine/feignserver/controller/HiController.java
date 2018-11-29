package com.nine.feignserver.controller;

import com.nine.feignserver.interfacee.SchedualHiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： 月在未央
 * @date： 2018/11/29 15:09
 * @Description：
 */
@RestController
public class HiController {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") //忽略编译器报错
    @Autowired
    SchedualHiService schedualHiService;

    @GetMapping(value = "/hi")
    public String sayHi(@RequestParam String name){
        return schedualHiService.sayHiFromClient(name);
    }
}

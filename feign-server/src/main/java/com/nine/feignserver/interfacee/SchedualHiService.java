package com.nine.feignserver.interfacee;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author： 月在未央
 * @date： 2018/11/29 15:05
 * @Description：
 */
@FeignClient(value = "client",fallback = SchedualServiceHiHystric.class)
public interface SchedualHiService {

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClient(@RequestParam(value = "name")String name);
}

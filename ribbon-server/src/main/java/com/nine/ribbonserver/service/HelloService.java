package com.nine.ribbonserver.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author： 月在未央
 * @date： 2018/11/29 13:59
 * @Description：
 */
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError") //定义服务调用失败时的熔断方法
    public String hiService(String name){
        return  restTemplate.getForObject("http://client/hi?name="+name,String.class);
    }

    //熔断方法
    public String hiError(String name){
        return "hi,"+name+",sorry error";
    }

}

package com.nine.feignserver.interfacee;

import org.springframework.stereotype.Component;

/**
 * @author： 月在未央
 * @date： 2018/11/29 16:13
 * @Description：
 */
@Component
public class SchedualServiceHiHystric implements SchedualHiService{
    @Override
    public String sayHiFromClient(String name) {
        return "sorry,"+name;
    }
}

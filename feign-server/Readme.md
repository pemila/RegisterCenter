# Feign-Server

* Feign 采用的是基于接口的注解
* Feign 整合了ribbon，具有负载均衡的能力
* 整合了Hystrix，具有熔断的能力

# 创建Feign客户端服务
1.配置pom.xml

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
2.配置application.yml，注册服务

    eureka:
      client:
        serviceUrl:
          defaultZone: http://localhost:8000/eureka/
3.启动类添加@EnableFeignClients注解开启Feign功能

    @SpringBootApplication
    @EnableEurekaClient
    @EnableDiscoveryClient
    @EnableFeignClients
    public class FeignServerApplication {
        public static void main(String[] args) {
            SpringApplication.run(FeignServerApplication.class, args);
        }
    
    }
4.定义feign接口，通过@FeignClient（“服务名”）指定调用的服务，方法注解@RequestMapping指定调用的方法

    @FeignClient(value = "client")
    public interface SchedualHiService {
        @RequestMapping(value = "/hi",method = RequestMethod.GET)
        String sayHiFromClient(@RequestParam(value = "name")String name);
    }
5.定义controller,对外暴露接口，通过SchedualHiService消费服务

    @RestController
    public class HiController {

        @Autowired
        SchedualHiService schedualHiService;
        
        @GetMapping(value = "/hi")
        public String sayHi(@RequestParam String name){
            return schedualHiService.sayHiFromClient(name);
        }
    }
6.启动程序多次访问http://127.0.0.1:8020/hi?name=123，浏览器交替出现如下结果，表示配置成功

    hi 123,i am from port:8001
    
    hi 123,i am from port:8002


# 启用Hystrix断路器
1.application.yml中打开Hystrix配置(注意此项配置idea不会提示，直接写入即可)

    hystrix:
      metrics:
        enabled: true
2.修改接口，在FeignClient注解上声明fallback的指定类

    @FeignClient(value = "client",fallback = SchedualServiceHiHystric.class)
    public interface SchedualHiService {
    
        @RequestMapping(value = "/hi",method = RequestMethod.GET)
        String sayHiFromClient(@RequestParam(value = "name")String name);
    }
3.定义SchedualServiceHiHystric.class,需要实现SchedualHiService并注入ioc容器

    @Component
    public class SchedualServiceHiHystric implements SchedualHiService{
        @Override
        public String sayHiFromClient(String name) {
            return "sorry,"+name;
        }
    }
4.访问http://127.0.0.1:8020/hi?name=123


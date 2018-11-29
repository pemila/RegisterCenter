# Ribbon-Server
负载均衡客户端。

注入org.springframework.web.client.RestTemplate对象，同时对其添加@LoadBalanced注解启动服务器负载均衡。

# 创建ribbon负载均衡客户端
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
2.配置application.yml,声明服务注册中心

    eureka:
      client:
        serviceUrl:
          defaultZone: http://localhost:8000/eureka/
3.在启动类添加注解@EnableDiscoveryClient向服务中心注册

    @SpringBootApplication
    @EnableEurekaClient
    @EnableDiscoveryClient
    public class RibbonServerApplication {
        ...
    }
4.在启动类中注入RestTemplate并通过@LoadBalanced开启负载均衡功能

    @Bean
    @LoadBalanced //开启负载均衡
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
5.创建测试类HelloService,通过ioc注入的RestTemplate调用其他服务提供者提供的服务

    @Service
    public class HelloService {
    
        @Autowired
        RestTemplate restTemplate;
    
        @HystrixCommand(fallbackMethod = "hiError") //定义服务调用失败时的熔断方法
        public String hiService(String name){
            return  restTemplate.getForObject("http://client/hi?name="+name,String.class);
        }
    }
6.创建controller,调用HelloService中的方法

    @RestController
    public class HelloController {
        @Autowired
        HelloService helloService;
    
        @GetMapping
        public String hi(@RequestParam String name){
            return helloService.hiService(name);
        }
    }
7.启动项目并访问http://127.0.0.1:8010/hi?name=123，浏览器交替出现如下结果，则表明负载均衡配置成功

    hi 123,i am from port:8001
    
    hi 123,i am from port:8002
    


# 添加HyStrix断路器
1.pom.xml加入hystri依赖
   
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
    
2.RibbonServerApplication添加注解

    @EnableHystrix
    
3.修改service类

    @HystrixCommand(fallbackMethod = "hiError") //定义服务调用失败时的熔断方法
    public String hiService(String name){
        return  restTemplate.getForObject("http://client/hi?name="+name,String.class);
    }
    
    //熔断方法
    public String hiError(String name){
        return "hi,"+name+",sorry error";
    }
4.关闭client服务，访问http://127.0.0.1:8010/hi?name=123
 
 
 
    

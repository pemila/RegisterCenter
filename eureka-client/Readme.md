# Eureka-Client
服务提供者，所有需要在注册中心注册的服务均需要继承本服务

# 创建服务提供者
1.配置pom.xml

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
2.通过注解@EnableEurekaClient 表明自己是一个eurekaclient

    @SpringBootApplication
    @EnableEurekaClient  //声明为eurekaclient
    @RestController
    public class EurekaClientApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(EurekaClientApplication.class, args);
        }
    
        @Value("${server.port}")
        String port;
    
        @RequestMapping("/hi")
        public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
            return "hi " + name + " ,i am from port:" + port;
        }
    
    }
3.在application.yml文件中声明服务注册中心的地址，同时指定本服务名称

    spring:
      application:
        name: client
    
    eureka:
      client:
        serviceUrl:
          defaultZone: http://localhost:8000/eureka/
4.启动项目并查看服务注册中心首页client服务是否已注册成功

5.提供的服务访问路径：http://127.0.0.1:8001/hi?name=123

# IDEA启动多实例
> ↗ 选择edit Configurations...

> ↗ 取消Single instance only上的√，apply

> 修改application.yml中端口号配置，多次启动即可

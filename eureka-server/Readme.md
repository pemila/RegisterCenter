# Eureka-Server
eureka服务注册中心。

# 创建服务注册中心
1.修改pom.xml加入eurka.server依赖


    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
2.EurekaServerApplication添加注解

    @EnableEurekaServer  //启用Eureka服务注册中心
    @SpringBootApplication
    public class EurekaServerApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(EurekaServerApplication.class, args);
        }
    }

3.配置application.yml文件

    eureka:
      instance:
        hostname: localhost
      client:
        #通过registerWithEureka：false和fetchRegistry：false来表明自己是一个eureka server
        registerWithEureka: false
        fetchRegistry: false
        serviceUrl:
          defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
4.启动后访问http://127.0.0.1:8000/






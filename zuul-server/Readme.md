# Zuul-Server
```
spirngcloud微服务系统中，负载均衡模式如下
    客户端请求-> 负载均衡(zuul\nginx)->服务网关(zuul集群)-> 具体服务

所有服务统一注册到高可用的服务注册中心集群
    服务的所有配置文件由配置服务管理
        配置服务的配置文件存储在git仓库进行管理
```
* Zuul的主要功能是路由转发和过滤器。
* 路由功能是微服务的一部分，比如／api/user转发到到user服务，/api/shop转发到到shop服务。
* zuul默认和Ribbon结合实现了负载均衡的功能

# Zuul网关
1.配置pom.xml

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
    </dependency>
2.在启动类添加@EnableZuulProxy注解开启zuul功能

    @SpringBootApplication
    @EnableZuulProxy //开启zuul功能
    @EnableEurekaClient
    @EnableDiscoveryClient
    public class ZuulServerApplication {
        public static void main(String[] args) {
            SpringApplication.run(ZuulServerApplication.class, args);
        }
    }
3.添加application.yml配置

    #在服务中心注册zuul服务
    eureka:
      client:
        serviceUrl: #声明服务注册中心地址
          defaultZone: http://localhost:8000/eureka/
    
    #zuul路由配置
    zuul:
      routes:
        api-a:
          path: /api-a/**   #指定请求的路径
          serviceId: ribbon  #指定请求的服务
        api-b:
          path: /api-b/**
          serviceId: feign
4.启动服务，访问如下路径，验证结果 ：
    
    http://localhost:8030/api-b/hi?name=123
    http://localhost:8030/api-a/hi?name=123
    
# 服务过滤
1.定义filter类继承zuulfilter

    @Component
    public class MyServiceFilter extends ZuulFilter {
        @Override
        public String filterType() {
            //返回过滤器的类型，pre\routing\post\error,分别表示路由之前、路由时、路由后、发送错误调用
            return "pre";
        }
        @Override
        public int filterOrder() {
            //当前过滤器的顺序
            return 0;
        }
        @Override
        public boolean shouldFilter() {
            //TODO 判定是否需要过滤，true=是，false=否
            return true;
        }
        @Override
        public Object run() throws ZuulException {
            //TODO 过滤器具体逻辑
            return null;
        }
    }
2.加入shouldFilter和run的逻辑

3.访问如下url,验证结果：

    http://localhost:8030/api-a/hi?name=forezp
    http://localhost:8030/api-a/hi?name=forezp&token=456
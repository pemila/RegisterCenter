package com.nine.zuulserver.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.nine.zuulserver.util.Logs;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author： 月在未央
 * @date： 2018/11/30 11:09
 * @Description：
 */
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
        RequestContext content = RequestContext.getCurrentContext();
        HttpServletRequest request = content.getRequest();
        Logs.info("当前请求：{}>>>{}",request.getMethod(),request.getRequestURL().toString());
        Object accessToken = request.getParameter("token");
        if(accessToken==null){
            Logs.warn("token of the request is empty");
            content.setSendZuulResponse(false);
            content.setResponseStatusCode(401);
            try{
                content.getResponse().getWriter().write("this request has no permission");
            }catch (IOException e){
                Logs.error("response操作异常：{}",e);
                return null;
            }
        }
        Logs.info("request is ok");
        return null;
    }
}

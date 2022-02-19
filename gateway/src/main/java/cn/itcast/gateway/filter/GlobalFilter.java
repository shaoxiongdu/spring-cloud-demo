package cn.itcast.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @date 2022/2/19 14:34
 */
@Component
@Order(-1)
public class GlobalFilter implements org.springframework.cloud.gateway.filter.GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response1 = exchange.getResponse();
        
        //获取参数
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();

        String auth = queryParams.getFirst("auth");

        if(Objects.nonNull(auth) && "admin".equals(auth)){
            //如果是管理员  则放行
            return chain.filter(exchange);
        }

        //拦截
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}

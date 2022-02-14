package cn.itcast.order.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @date 2022/2/14 18:51
 */
public class FeignClientConfiguration {

    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.BASIC;
    }

}

package cn.itcast.order.client;

import cn.itcast.order.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @date 2022/2/14 18:47
 */
@FeignClient("user-service")
public interface UserClient {
    /**
     * 查询用户信息
     * @param id 用户Id
     * @return 用户信息
     */
    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);
}

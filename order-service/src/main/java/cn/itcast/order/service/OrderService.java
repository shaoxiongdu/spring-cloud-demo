package cn.itcast.order.service;

import cn.itcast.feign.client.UserClient;
import cn.itcast.feign.pojo.Order;
import cn.itcast.feign.pojo.User;
import cn.itcast.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Resource
    private UserClient userClient;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

        //请求地址
        String url = "http://user-service/user/" + order.getUserId();

        //远程调用用户服务
        User user = userClient.findById(order.getUserId());

        order.setUser(user);

        // 4.返回
        return order;
    }
}

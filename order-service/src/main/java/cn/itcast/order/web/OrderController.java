package cn.itcast.order.web;


import cn.itcast.feign.pojo.Order;
import cn.itcast.order.service.OrderService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author dushaoxiong
 */
@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private OrderService orderService;

   @NacosValue("${pattern.dateformat}")
   private String dateFormat;

    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单并返回
        return orderService.queryOrderById(orderId);
    }

    @GetMapping("/now")
    public String now(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateFormat));
    }
}

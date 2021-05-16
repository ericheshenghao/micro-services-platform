package cn.central.order.controller;


import cn.central.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : heshenghao
 * @date : 19:01 2021/5/15
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 创建订单
     * @param userId 用户id
     * @param commodityCode 订单编号
     * @param count 数量
     */
    @RequestMapping("/create")
    public Boolean create(String userId, String commodityCode, Integer count) {
        orderService.create(userId, commodityCode, count);
        return true;
    }
}

package cn.central.order.service;


import cn.central.order.feign.AccountFeignClient;
import cn.central.order.mapper.OrderMapper;
import cn.central.order.model.Order;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : heshenghao
 * @date : 19:05 2021/5/15
 */
@Slf4j
@Service
public class OrderService {
    @Resource
    private AccountFeignClient accountFeignClient;

    @Resource
    private OrderMapper orderMapper;

    public void create(String userId, String commodityCode, Integer count) {
        // 订单金额
        Integer orderMoney = count * 2;

        Order order = new Order()
                .setUserId(userId)
                .setCommodityCode(commodityCode)
                .setCount(count)
                .setMoney(orderMoney);
        orderMapper.insert(order);

        accountFeignClient.reduce(userId, orderMoney);

    }
}

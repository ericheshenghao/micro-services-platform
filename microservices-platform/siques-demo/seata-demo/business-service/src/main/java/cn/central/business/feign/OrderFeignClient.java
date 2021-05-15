package cn.central.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : heshenghao
 * @date : 19:16 2021/5/15
 */
@FeignClient(name = "order-service")
public interface OrderFeignClient {

    @GetMapping("order/create")
    Boolean create(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count);
}
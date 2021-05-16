package cn.central.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : heshenghao
 * @date : 19:27 2021/5/15
 */
@FeignClient(name = "account-service")
public interface AccountFeignClient {
    @PostMapping("account/reduce")
    Boolean reduce(@RequestParam("userId") String userId, @RequestParam("money") Integer money);
}
package cn.central.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 断路器
 * @author he
 */
@RestController
public class FallbackController {
    @RequestMapping(value = "/fallback")
    public String fallback(){
        return "fallback nothing";
    }

}

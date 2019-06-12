package com.bxy.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
        @GetMapping("/auth")
        public void auth(@RequestParam("code") String code){
                log.info("auth method....");
                log.info("code={}", code);

                String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx38e9831e438e3c1d&secret=8fc562940793d9e74ab7f68c1e9e7f81&code="+code+"&grant_type=authorization_code";
                RestTemplate restTemplate = new RestTemplate();
                String response = restTemplate.getForObject(url,String.class);
                log.info("response={}",response);

        }
}

package com.bxy.sell.controller;

import com.bxy.sell.constant.CookieConstant;
import com.bxy.sell.constant.RedisConstant;
import com.bxy.sell.dataobject.SellerInfo;
import com.bxy.sell.enums.ResultEnum;
import com.bxy.sell.service.SellerService;
import com.bxy.sell.utils.CookieUtil;
import jdk.nashorn.internal.ir.IdentNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid ,
                              Map<String,Object> map,
                              HttpServletResponse response
                              ){
        //1.openid与数据库去匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo == null){
           map.put("msg", ResultEnum.LOGIN_FAIL);
           map.put("url","/sell/seller/order/list");
           return new ModelAndView("pay/common/error");
        }
        //生成token
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        //2.设置token到redis
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);
        //3.设置token到cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);
        return new ModelAndView("redirect:"+"http://yushen.natapp1.cc/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                        HttpServletResponse response,
                        Map<String , Object> map
                       ){
        //1.从cookie中查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null){
            //2.清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //.清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS);
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/pay/common/success",map);
    }
}

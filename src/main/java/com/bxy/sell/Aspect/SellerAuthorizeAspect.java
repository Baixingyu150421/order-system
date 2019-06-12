package com.bxy.sell.Aspect;

import com.bxy.sell.constant.CookieConstant;
import com.bxy.sell.constant.RedisConstant;
import com.bxy.sell.exception.SellerAuthorizeException;
import com.bxy.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.data.redis.core.StringRedisTemplate;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 卖家授权切面
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;
    //切入点
    @Pointcut("execution(public * com.bxy.sell.controller.Seller*.*(..)) " +
            "&& !execution(public * com.bxy.sell.controller.SellerUserController.*(..))")
    public void verify(){

    }

    //前置
    @Before("verify()")
    public void doVerify(){
        //获取请求参数
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie == null){
            log.warn("【登录校验】 Cookie中查不到token");
            throw new SellerAuthorizeException();
        }

        //去redis中查询
        String tokenValue =
                redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("【登录校验】 redis中查不到token");
            throw new SellerAuthorizeException();
        }

    }

}

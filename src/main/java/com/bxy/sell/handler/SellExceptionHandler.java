package com.bxy.sell.handler;

import com.bxy.sell.ViewObject.ResultVO;
import com.bxy.sell.exception.ResponseBankException;
import com.bxy.sell.exception.SellException;
import com.bxy.sell.exception.SellerAuthorizeException;
import com.bxy.sell.utils.ResultVOUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理类
 */
@ControllerAdvice
public class SellExceptionHandler {
    //http://yushen.natapp1.cc/sell/wecaht/qrAuthorize?returnUrl=http://yushen.natapp1.cc/sell/seller/login
    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redirect:"
                .concat("http://yushen.natapp1.cc")
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat("http://yushen.natapp1.cc")
                .concat("/sell/seller/login")
        );
    }
    //sellException异常处理
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }
    //返回状态码403
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handlerBankException(){

    }
}

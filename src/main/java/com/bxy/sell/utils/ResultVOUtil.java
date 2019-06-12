package com.bxy.sell.utils;

import com.bxy.sell.ViewObject.ResultVO;

public class ResultVOUtil {
    /**
     * 方法调用成功是调用的方法
     * @param object
     * @return
     */
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    /**
     * 成功但不传数据
     * @return
     */
    public static ResultVO success(){
      return  success(null);
    }

    /**
     * 失败
     * @param code
     * @param msg
     * @return
     */
    public static ResultVO error(Integer code , String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}

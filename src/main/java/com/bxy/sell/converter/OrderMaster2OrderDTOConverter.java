package com.bxy.sell.converter;

import com.bxy.sell.dataobject.OrderMaster;
import com.bxy.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对象类型转换器
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
//        List<OrderDTO> orderDTOList = new ArrayList<>();
//        BeanUtils.copyProperties(orderMasterList,orderDTOList);
//        return orderDTOList;
        return orderMasterList.stream().map(e ->convert(e)).collect(Collectors.toList());
    }


}

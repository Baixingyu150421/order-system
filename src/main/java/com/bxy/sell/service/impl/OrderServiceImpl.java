package com.bxy.sell.service.impl;

import com.bxy.sell.converter.OrderMaster2OrderDTOConverter;
import com.bxy.sell.dataobject.OrderDetail;
import com.bxy.sell.dataobject.OrderMaster;
import com.bxy.sell.dataobject.ProductInfo;
import com.bxy.sell.dto.CartDTO;
import com.bxy.sell.dto.OrderDTO;
import com.bxy.sell.enums.OrderStatusEnum;
import com.bxy.sell.enums.PayStatusEnum;
import com.bxy.sell.enums.ResultEnum;
import com.bxy.sell.exception.ResponseBankException;
import com.bxy.sell.exception.SellException;
import com.bxy.sell.repository.OrderDetailRepository;
import com.bxy.sell.repository.OrderMasterRepository;
import com.bxy.sell.service.OrderService;
import com.bxy.sell.service.PayService;
import com.bxy.sell.service.ProductInfoService;
import com.bxy.sell.service.WebSocket;
import com.bxy.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository repository;
    @Autowired
    private ProductInfoService infoService;
    @Autowired
    private OrderMasterRepository masterRepository;
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private PayService payService;
    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //创建订单方法调用时就生成了订单ID
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1.查询商品（数量，单价）
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = infoService.findOne(orderDetail.getProductId());
            //商品不存在时，抛出异常
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
                //throw new ResponseBankException();

            }
            //2.计算总金额 (注意BigDecimal的乘法)
            orderAmount = productInfo.getProductPrice()
                    .multiply( new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            //Spring 提供的对象属性拷贝方法
            //订单详情入库（订单详情表与订单主表是多对一的关系）
            repository.save(orderDetail);
        }
        //设置订单主表的订单ID
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        //订单主表入库
        //3.写入数据库（orderMaster,orderDetail）
        masterRepository.save(orderMaster);
        //4.扣除库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(
                e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        infoService.decreaseStock(cartDTOList);

        //给商家发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    /**
     * 查找订单
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {
        //先查询主表
        OrderMaster orderMaster = masterRepository.findOne(orderId);
        //不存在抛出异常
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //查询订单明细表
        List<OrderDetail> orderDetailList = repository.findByOrderId(orderId);
        //不存在则抛出异常
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new  SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        //创建OrderDTO对象,并封装返回给前端
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    /**
     * 查询用户订单列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = masterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //1.首先查询订单状态(进行判断)
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确,orderId={}, orderStatus={},",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateMaster = masterRepository.save(orderMaster);
        if(updateMaster == null){
            log.error("【取消订单】更新失败 orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //3.返还下单时扣除的库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单]订单中无商品详情 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.OEDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().
                stream().map(e-> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        infoService.increaseStock(cartDTOList);

        //4.查询支付状态，已付款需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESSED.getCode())){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    /**
     * 完结订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //1.判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确， orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态
//        OrderMaster orderMaster = masterRepository.getOne(orderDTO.getOrderId());
//        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
//        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateMaster = masterRepository.save(orderMaster);
        if(updateMaster == null){
            log.error("【完结订单】 更新失败，orderMaster={}",orderMaster );
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    /**
     * 支付订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //1.判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】 订单状态不正确 orderId={} , orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】 订单支付状态不正确 orderId={} , payStatus={}" , orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }
        //3.修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESSED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateMaster = masterRepository.save(orderMaster);
        if(updateMaster == null){
            log.error("【支付订单】 更新失败 orderMaster" , orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    /**
     * 卖家端查询所有订单列表
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> masterRepositoryAll = masterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(masterRepositoryAll.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,masterRepositoryAll.getTotalElements());
        return orderDTOPage;
    }
}

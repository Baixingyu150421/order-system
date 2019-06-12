package com.bxy.sell.repository;

import com.bxy.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    /**
     * 根据用户的openID查询并分页
     * @param BuyerOpenId
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid (String BuyerOpenId , Pageable pageable);
}

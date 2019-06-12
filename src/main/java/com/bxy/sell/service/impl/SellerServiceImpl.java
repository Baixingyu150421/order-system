package com.bxy.sell.service.impl;

import com.bxy.sell.dataobject.SellerInfo;
import com.bxy.sell.repository.SellerInfoRepository;
import com.bxy.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }
}

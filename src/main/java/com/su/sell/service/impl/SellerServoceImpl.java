package com.su.sell.service.impl;

import com.su.sell.dataObject.SellerInfo;
import com.su.sell.repository.SellerInfoRepository;
import com.su.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServoceImpl implements SellerService {
    @Autowired
    private SellerInfoRepository sellerInfoRepository;
    @Override
    public SellerInfo findSellerInfoByOpenId(String openId) {
        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid(openId);
        return sellerInfo;
    }
}

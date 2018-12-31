package com.su.sell.service;

import com.su.sell.dataObject.SellerInfo;

public interface SellerService {
    SellerInfo findSellerInfoByOpenId(String openId);
}

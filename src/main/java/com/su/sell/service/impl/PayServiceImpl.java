package com.su.sell.service.impl;

import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.su.sell.dto.OrderMasterDTO;
import com.su.sell.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Override
    public void create(OrderMasterDTO orderMasterDTO) {
        PayRequest payRequest = new PayRequest();
        bestPayService.pay(payRequest);
    }
}

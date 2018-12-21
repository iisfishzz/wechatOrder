package com.su.sell.service.impl;

import com.su.sell.dto.OrderMasterDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {
    @Autowired
    private PayServiceImpl payService;
    @Test
    public void create() {
        OrderMasterDTO dto = new OrderMasterDTO();
        payService.create(dto);
    }
}
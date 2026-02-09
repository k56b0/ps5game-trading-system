package com.zxy.ps5gametradingsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.ps5gametradingsystem.entity.Order;
import com.zxy.ps5gametradingsystem.mapper.OrderMapper;
import com.zxy.ps5gametradingsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper mapper;
    @Override
    public List<Order> queryOrder(String id) {
        return mapper.selectOrderWithDetails(id);
    }

    @Override
    public Page<Order> queryOrderPage(Page<Order> page, String userId) {
        return this.baseMapper.selectOrderPage(page, userId);
    }
}

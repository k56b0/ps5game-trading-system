package com.zxy.ps5gametradingsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.ps5gametradingsystem.entity.Order;

import java.util.List;

public interface OrderService extends IService<Order> {
    // 原有的查询所有订单的方法（不分页）
    List<Order> queryOrder(String id);
    // 新增分页查询方法
    Page<Order> queryOrderPage(Page<Order> page, String userId);

}

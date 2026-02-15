package com.zxy.ps5gametradingsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.Order;
import com.zxy.ps5gametradingsystem.entity.Order_details;
import com.zxy.ps5gametradingsystem.service.OrderService;
import com.zxy.ps5gametradingsystem.service.Order_detailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private Order_detailsService order_detailsService;
    //查询所有订单
    @GetMapping("/queryAll")
    public Result queryAll(@RequestParam(defaultValue = "1") String userId,
                              @RequestParam(defaultValue = "1") Integer pageNum) {
        int pageSize = 2;
        // 创建分页对象
        Page<Order> page = new Page<>(pageNum, pageSize);
        // 调用分页查询方法
        Page<Order> orderPage = orderService.queryOrderPage(page, userId);
        System.out.println("查询成功");
        // 判断是否有数据
        if (orderPage != null && orderPage.getRecords() != null && !orderPage.getRecords().isEmpty()) {
            return Result.success(orderPage);
        } else {
            return Result.error();
        }
    }
    //添加订单
    @PostMapping("/add")
    public Result addOrders(@RequestBody Order o){
        orderService.save(o);
        for(Order_details item :o.getOrderDetails()){
            order_detailsService.save(item);
        }
        return Result.success();
    }
}

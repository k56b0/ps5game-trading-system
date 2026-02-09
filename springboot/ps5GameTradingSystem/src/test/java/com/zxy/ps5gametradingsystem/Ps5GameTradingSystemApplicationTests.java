package com.zxy.ps5gametradingsystem;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.zxy.ps5gametradingsystem.entity.Order;
import com.zxy.ps5gametradingsystem.entity.Order_details;
import com.zxy.ps5gametradingsystem.entity.User;
import com.zxy.ps5gametradingsystem.mapper.OrderMapper;
import com.zxy.ps5gametradingsystem.mapper.UserMapper;
import com.zxy.ps5gametradingsystem.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Ps5GameTradingSystemApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        String uerId="1";
        List<Order> order = orderMapper.selectOrderWithDetails(uerId);
        for(Order o :order){
            System.out.println(o);
        }

    }

}

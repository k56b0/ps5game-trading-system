package com.zxy.ps5gametradingsystem;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.zxy.ps5gametradingsystem.entity.User;
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
    private UserService userService;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}

package com.zxy.ps5gametradingsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.ps5gametradingsystem.entity.User;

public interface UserService extends IService<User> {
    // 登录方法，返回用户对象，如果登录失败则返回null
    User login(String email, String password);
    // 修改用户信息方法
    User update(User user);
    //用户注册
    boolean register(User user);
}


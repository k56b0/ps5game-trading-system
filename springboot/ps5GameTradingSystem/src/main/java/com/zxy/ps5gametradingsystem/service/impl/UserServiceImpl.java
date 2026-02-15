package com.zxy.ps5gametradingsystem.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.ps5gametradingsystem.entity.User;
import com.zxy.ps5gametradingsystem.mapper.UserMapper;
import com.zxy.ps5gametradingsystem.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User login(String email, String password) {
        // 1. 根据邮箱查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User user = this.getOne(queryWrapper); // 假设邮箱是唯一的
        // 2. 如果用户不存在，返回null
        if (user == null) {
            return null;
        }
        // 3. 比较密码
        if (password.equals(user.getPassword())) {
            // 4. 密码正确，返回用户
            return user;
        }
        // 5. 密码错误，返回null
        return null;
    }

    @Override
    public User update(User user) {
        this.updateById(user);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, user.getId());
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean register(User user) {
         //1.根据邮箱查询现有用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, user.getEmail());
        //2.如果邮箱不重复,就更新用户信息，并返回true
        if(this.getOne(queryWrapper) ==null){
            this.save(user);
            return true;
        }else{
            return false;
        }
    }


}
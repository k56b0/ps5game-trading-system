package com.zxy.ps5gametradingsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.*;
import com.zxy.ps5gametradingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    // 登录方法，参数改为邮箱和密码
    @PostMapping("/login")
    public Result login(@RequestBody User request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        if (user != null) {
            // 登录成功，返回用户信息
            return Result.success(user);
        } else {
            // 登录失败
            return Result.error();
        }
    }
    //用户保存用户信息
    @PostMapping("/update")
    public Result update (@RequestBody User request){
        User updateData = userService.update(request);
        if( updateData !=null){
            //保存成功
            System.out.println("用户保存用户信息");
            return Result.success(updateData);

        }else {
            //保存失败
            return Result.error();
        }
    }

    //用户注册
    @PostMapping("/register")
    public Result register (@RequestBody User request){
        if(userService.register(request)){
            //注册成功
            return Result.success();
        }else{
            //注册失败
            return Result.error(400,"邮箱已被注册",null);
        }
    }
    //找回密码
    @PostMapping("/findPassword")
    public Result findPassword (@RequestBody User user){
        // 1. 根据邮箱查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, user.getEmail());
        User u = userService.getOne(queryWrapper); // 假设邮箱是唯一的
        // 2. 如果用户不存在，返回null
        if (u == null) {
            return Result.error(400,"用户不存在",null);
        }
        // 3. 比较问题
        if (u.getAnswer().equals(user.getAnswer())) {
            // 4. 问题正确
            //修改密码
            u.setPassword(user.getPassword());
            userService.update(u);
            //返回正确提示
            return Result.success();
        }
        // 5. 问题错误
        return Result.error(400,"验证问题错误",null);
    }

    //查询全部用户信息 分页
    @GetMapping("/queryAll/{pageNum}")
    public Result  queryAll(@PathVariable Integer pageNum){
        int pageSize = 8;
        // 创建分页对象
        Page<User> page = new Page<>(pageNum, pageSize);
        // 执行分页查询
        Page<User> resultPage = userService.page(page);
        return Result.success(resultPage);
    }
    //删除游戏信息,测试成功
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id){
        userService.removeById(id);
        return  Result.success();
    }
    //添加用户（增）
    @PostMapping("/add")
    public Result add (@RequestBody User g){
        if(userService.save(g)){
            //添加成功
            return Result.success();
        }else{
            //添加失败
            return Result.error(400,"添加失败",null);
        }
    }
    //签到
    @PostMapping("/checkIn")
    public Result checkIn(@RequestParam String id) {
        // 1. 查询用户
        User existingUser = userService.getById(id);
        if (existingUser == null) {
            return Result.error(404, "用户不存在", null);
        }

        Date now = new Date();
        Date lastCheckTime = existingUser.getCheckTime();

        // 2. 判断签到间隔（首次签到或超过24小时）
        boolean canCheckIn = (lastCheckTime == null);
        if (!canCheckIn) {
            long diffHours = (now.getTime() - lastCheckTime.getTime()) / (1000 * 60 * 60);
            if (diffHours > 24) {
                canCheckIn = true;
            }
        }

        if (!canCheckIn) {
            return Result.error(400, "间隔时间不足24h，不能重复签到", null);
        }

        // 3. 执行签到
        existingUser.setCheckIn(existingUser.getCheckIn() + 1);
        existingUser.setCheckTime(now);
        boolean updated = userService.updateById(existingUser);

        if (updated) {
            // 返回最新签到天数
            return Result.success(existingUser.getCheckIn());
        } else {
            return Result.error(500, "签到失败，请重试", null);
        }
    }
}

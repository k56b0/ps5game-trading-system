package com.zxy.ps5gametradingsystem.controller;


import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.User;
import com.zxy.ps5gametradingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}

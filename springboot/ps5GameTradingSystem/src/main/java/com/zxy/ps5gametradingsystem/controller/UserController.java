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
//
//    /**
//     *
//     * @param email
//     * @param passw
//     * @return
//     */
//    // 登录方法，参数改为邮箱和密码
//    @GetMapping("/login/{email}/{passw}")
//    public Result login(@PathVariable String email, @PathVariable String passw) {
//        User user = userService.login(email, passw);
//        if (user != null) {
//            // 登录成功，返回用户信息（注意：不要返回密码）
////            user.setPassw(null); // 将密码置空，不返回给前端
//            return Result.success(user);
//        } else {
//            // 登录失败
//            return Result.error();
//        }
//    }


    // 登录方法，参数改为邮箱和密码
    @PostMapping("/login")
    public Result login(@RequestBody User request) {
        System.out.println(request);
        User user = userService.login(request.getEmail(), request.getPassw());
        System.out.println(user);
        if (user != null) {
            // 登录成功，返回用户信息
            return Result.success(user);
        } else {
            // 登录失败
            return Result.error();
        }
    }

}

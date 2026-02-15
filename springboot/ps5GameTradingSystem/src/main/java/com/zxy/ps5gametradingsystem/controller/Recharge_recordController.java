package com.zxy.ps5gametradingsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.Recharge_record;
import com.zxy.ps5gametradingsystem.entity.Recycle;
import com.zxy.ps5gametradingsystem.entity.User;
import com.zxy.ps5gametradingsystem.service.Recharge_recordService;
import com.zxy.ps5gametradingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recharge_record")
public class Recharge_recordController {
    @Autowired
    private Recharge_recordService rechargeRecordService;
    @Autowired
    private UserService userService;

    //充值
    @PutMapping("/save")
    public Result save (@RequestBody Recharge_record s) {
        // 保存充值记录
        rechargeRecordService.save(s);
        // 1. 先查询用户当前余额
        User user = userService.getById(s.getUserId()); // 假设充值记录中有userId字段
        if (user != null) {
            // 2. 计算新的余额（当前余额 + 充值金额）
            Integer newBalance = user.getMoney()+s.getQuantity();
            // 3. 创建要更新的用户对象
            User updateUser = new User();
            updateUser.setId(s.getUserId());
            updateUser.setMoney(newBalance);
            // 4. 更新用户余额
            boolean updated = userService.updateById(updateUser);

            if (updated) {
                return Result.success("充值成功");
            } else {
                return Result.error();
            }
        } else {
            return Result.error();
        }

    }
    //查询所有充值订单 分页
    @GetMapping("/queryAll/{id}/{pageNum}")
    public Result queryAll(@PathVariable String id, @PathVariable Integer pageNum){
        int pageSize = 2;
        // 创建分页对象
        Page<Recharge_record> page = new Page<>(pageNum, pageSize);
        // 构建查询条件
        LambdaQueryWrapper<Recharge_record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recharge_record::getUserId, id);
        // 执行分页查询
        Page<Recharge_record> resultPage = rechargeRecordService.page(page, queryWrapper);
        return Result.success(resultPage);
    }
}

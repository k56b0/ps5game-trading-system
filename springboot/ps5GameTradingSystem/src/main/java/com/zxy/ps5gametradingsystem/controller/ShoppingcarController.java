package com.zxy.ps5gametradingsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.Order;
import com.zxy.ps5gametradingsystem.entity.Order_details;
import com.zxy.ps5gametradingsystem.entity.Shoppingcar;
import com.zxy.ps5gametradingsystem.service.ShoppingcarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingcar")
public class ShoppingcarController {
    @Autowired
    private ShoppingcarService shoppingcarService;
    @Autowired
    private OrderController orderController;
    //添加进购物车（增） ,测试成功
    @PostMapping("/add")
    public Result add (@RequestBody Shoppingcar s){
        LambdaQueryWrapper<Shoppingcar> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Shoppingcar::getUserId, s.getUserId())
                .eq(Shoppingcar::getGameName, s.getGameName());
        Shoppingcar obj = shoppingcarService.getOne(queryWrapper);
        if(obj == null){
            shoppingcarService.save(s);
            return Result.success();
        }else {
            int temp = obj.getQuantity() + s.getQuantity();
            // 假设有一个 UpdateWrapper 对象，
            // 设置更新条件为 user_id = s.getUserId()，game_name=s.getGameName()
            // 更新字段为quantity
            UpdateWrapper<Shoppingcar> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", s.getUserId()).
                    eq("game_name", s.getGameName()).
                    set("quantity", temp);
            boolean result = shoppingcarService.update(updateWrapper); // 调用 update 方法
            if (result) {

                return Result.success();
            } else {
                return Result.error();
            }
        }
    }
    //删除购物车信息,测试成功
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id){
        shoppingcarService.removeById(id);
        return  Result.success();
    }
    //查询全部购物车 分页,测试成功
    @GetMapping("/queryAll/{id}/{pageNum}")
    public Result queryAll (@PathVariable String id,@PathVariable Integer pageNum){
        int pageSize = 5;
        // 创建分页对象
        Page<Shoppingcar> page = new Page<>(pageNum, pageSize);
        // 构建查询条件
        LambdaQueryWrapper<Shoppingcar> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Shoppingcar::getUserId, id);
        // 执行分页查询
        Page<Shoppingcar> resultPage = shoppingcarService.page(page, queryWrapper);
        return Result.success(resultPage);
    }
    //从购物车添加到订单,测试成功
    @PostMapping("/addToOrder")
    public Result addToOrder (@RequestBody Order o) {
        orderController.addOrders(o);
        //删除购物车里的信息
        for(Order_details item :o.getOrderDetails()){
            LambdaQueryWrapper<Shoppingcar> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Shoppingcar::getUserId, o.getUserId())
                    .eq(Shoppingcar::getGameName, item.getGameName());
            shoppingcarService.remove(queryWrapper);
        }
        return Result.success();
    }
}

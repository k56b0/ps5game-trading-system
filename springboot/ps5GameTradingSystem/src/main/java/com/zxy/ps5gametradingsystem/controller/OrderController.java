package com.zxy.ps5gametradingsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.Game;
import com.zxy.ps5gametradingsystem.entity.Order;
import com.zxy.ps5gametradingsystem.entity.Order_details;
import com.zxy.ps5gametradingsystem.entity.User;
import com.zxy.ps5gametradingsystem.service.GameService;
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
    @Autowired
    private GameService gameService;
    //查询所有订单
    @GetMapping("/queryAll")
    public Result queryAll(@RequestParam(defaultValue = "1") String userId,
                              @RequestParam(defaultValue = "1") Integer pageNum) {
        int pageSize = 2;
        // 创建分页对象
        Page<Order> page = new Page<>(pageNum, pageSize);
        // 调用分页查询方法
        Page<Order> orderPage = orderService.queryOrderPage(page, userId);
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
        // 建议开启事务（如使用 @Transactional）
        orderService.save(o);
        for (Order_details item : o.getOrderDetails()) {
            order_detailsService.save(item);
            // 每次创建新的查询条件
            LambdaQueryWrapper<Game> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Game::getGameName, item.getGameName());

            Game updateGame = gameService.getOne(queryWrapper);
            if (updateGame == null) {
                // 游戏不存在，回滚或返回错误（根据业务决定）
                return Result.error();
            }
            // 更新库存和销量
            updateGame.setInventory(updateGame.getInventory() - item.getQuantity());
            updateGame.setSales(updateGame.getSales() + item.getQuantity());
            boolean updated = gameService.updateById(updateGame);
            if (!updated) {
                // 更新失败，可根据需要回滚事务（抛出运行时异常）
                return Result.error();
            }
        }
        // 所有详情处理成功
        return Result.success("添加成功");
    }
}


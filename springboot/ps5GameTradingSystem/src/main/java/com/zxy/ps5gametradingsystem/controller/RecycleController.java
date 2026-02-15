package com.zxy.ps5gametradingsystem.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.Recycle;
import com.zxy.ps5gametradingsystem.service.RecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recycle")
public class RecycleController {
    @Autowired
    private RecycleService recycleService;
    @PostMapping("/save")
    public Result save (@RequestBody Recycle s) {
        recycleService.save(s);
        return Result.success();

    }
    //查询所有回收订单
    //查询用户全部回收游戏信息 分页
    @GetMapping("/queryAll/{id}/{pageNum}")
    public Result queryAll(@PathVariable String id, @PathVariable Integer pageNum){
        int pageSize = 2;
        // 创建分页对象
        Page<Recycle> page = new Page<>(pageNum, pageSize);
        // 构建查询条件
        LambdaQueryWrapper<Recycle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recycle::getUserId, id);
        // 执行分页查询
        Page<Recycle> resultPage = recycleService.page(page, queryWrapper);
        return Result.success(resultPage);
    }
}

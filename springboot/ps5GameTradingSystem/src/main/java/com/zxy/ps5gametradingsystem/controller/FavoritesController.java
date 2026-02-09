package com.zxy.ps5gametradingsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.Favorites;
import com.zxy.ps5gametradingsystem.entity.Shoppingcar;
import com.zxy.ps5gametradingsystem.service.FavoritesService;
import com.zxy.ps5gametradingsystem.service.ShoppingcarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {
    @Autowired
    private FavoritesService favoritesService;
    @Autowired
    private ShoppingcarService shoppingcarService;
    //添加进收藏夹（增）
    @PostMapping("/save")
    public Result save (@RequestBody Favorites f){
        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorites::getUserId, f.getUserId())
                    .eq(Favorites::getGameName, f.getGameName());
        if(favoritesService.getOne(queryWrapper) ==null){
            favoritesService.save(f);
            return Result.success();
        }else{
            return  Result.error();
        }
    }
    //删除收藏夹信息
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id){
        favoritesService.removeById(id);
        return  Result.success();
    }
    //查询用户全部收藏夹信息 分页
    @GetMapping("/{id}/{pageNum}")
    public Result list (@PathVariable String id,@PathVariable Integer pageNum){
        int pageSize = 2;
        // 创建分页对象
        Page<Favorites> page = new Page<>(pageNum, pageSize);
        // 构建查询条件
        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorites::getUserId, id);
        // 执行分页查询
        Page<Favorites> resultPage = favoritesService.page(page, queryWrapper);
        return Result.success(resultPage);
    }
    //添加到购物车
    @PutMapping("/addCar")
    public Result addCar (@RequestBody Shoppingcar s) {
        shoppingcarService.save(s);
        return Result.success();
    }
}

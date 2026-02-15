package com.zxy.ps5gametradingsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.Favorites;
import com.zxy.ps5gametradingsystem.entity.Game;
import com.zxy.ps5gametradingsystem.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    //根据游戏 ID 查询单个游戏信息
    @GetMapping("/queryByID/{id}")
    public Result  queryByID(@PathVariable String id){
        Game g = gameService.getById(id);
        if(g!=null){
            return Result.success(g);
        }else{
            return Result.error();
        }
    }
    //根据游戏名称查询单个游戏信息
    @GetMapping("/queryByName/{name}")
    public Result  queryByName(@PathVariable String name){
        LambdaQueryWrapper<Game> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Game::getGameName, name);
        Game g = gameService.getOne(queryWrapper);
        if(g !=null){
            return Result.success(g);
        }else{
            return  Result.error();
        }
    }
    //查询全部游戏信息 分页
    @GetMapping("/queryAll/{pageNum}")
    public Result  queryAll(@PathVariable Integer pageNum){
        int pageSize = 4;
        // 创建分页对象
        Page<Game> page = new Page<>(pageNum, pageSize);
        // 执行分页查询
        Page<Game> resultPage = gameService.page(page);
        return Result.success(resultPage);
    }


    //根据游戏种类查询游戏合计
    @GetMapping("/queryByCategory")
    public Result  queryByCategory(){
        try {
            // 1. 查询所有游戏
            List<Game> allGames = gameService.list();
            // 2. 按游戏种类分组
            Map<String, List<Game>> gamesByCategory = allGames.stream()
                    .filter(game -> game.getCategory() != null) // 过滤掉种类为null的游戏
                    .collect(Collectors.groupingBy(Game::getCategory));

            if (!gamesByCategory.isEmpty()) {
                return Result.success(gamesByCategory);
            } else {
                return Result.success();
            }
        } catch (Exception e) {
            return Result.error();
        }

    }
    //根据销量排序，查询全部
    @GetMapping("/bySales")
    public Result  bySales(){
        // 使用 QueryWrapper 进行排序
        LambdaQueryWrapper<Game> queryWrapper = new LambdaQueryWrapper<>();
        // 按销量降序，如果销量相同按游戏名称升序
        queryWrapper.orderByDesc(Game::getSales)
                .orderByAsc(Game::getGameName);

        List<Game> list = gameService.list(queryWrapper);

        if(list != null && !list.isEmpty()){
            return Result.success(list);
        } else {
            return Result.error();
        }
    }
}

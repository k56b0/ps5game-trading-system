package com.zxy.ps5gametradingsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxy.ps5gametradingsystem.common.Result;
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
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    //根据游戏 ID 查询单个游戏信息,或者根据游戏名称查询单个游戏信息
    @GetMapping("/queryById/{id}")
    public Result  queryById(@PathVariable String id){
        Game g = gameService.getById(id);
        if(g!=null){
            return Result.success(g);
        }else{
            return Result.error();
        }
    }
    //查询全部游戏信息
    @GetMapping("/all")
    public Result  queryAll(){
        List<Game> list = gameService.list();
        if(list!=null){
            return Result.success(list);
        }else{
            return Result.error();
        }
    }

    //根据游戏种类查询游戏合计
    @GetMapping("/byCategory")
    public Result  byCategory(){
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

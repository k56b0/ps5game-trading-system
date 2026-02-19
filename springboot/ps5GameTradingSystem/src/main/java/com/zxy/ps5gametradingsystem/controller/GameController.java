package com.zxy.ps5gametradingsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.Game;
import com.zxy.ps5gametradingsystem.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;
    boolean isByDescA=true;
    boolean isByDescB=true;
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
        //查询条件：查同名的游戏
        LambdaQueryWrapper<Game> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Game::getGameName, name);
        Game g = gameService.getOne(queryWrapper);
        // 返回查到的游戏
        if(g !=null){
            return Result.success(g);
        }else{
            return  Result.error();
        }
    }
    //查询全部游戏信息 分页
    @GetMapping("/queryAll/{pageNum}")
    public Result  queryAll(@PathVariable Integer pageNum){
        int pageSize = 8;
        // 创建分页对象
        Page<Game> page = new Page<>(pageNum, pageSize);
        // 执行分页查询
        Page<Game> resultPage = gameService.page(page);
        return Result.success(resultPage);
    }
    // 查询 热销商品/新品上市/即将推出 前三甲 Push page
    @GetMapping("/queryPushPage/{choice}")
    public Result  queryPushPage(@PathVariable String choice){
        int pageNum=1;
        int pageSize = 4;
        // 创建分页对象
        Page<Game> page = new Page<>(pageNum, pageSize);
        //创建查询条件
        LambdaQueryWrapper<Game> queryWrapper = new LambdaQueryWrapper<>();
        // 获取当前日期并格式化为 yyyy-MM-dd 字符串
        //  String todayStr = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        //假设 今天是 2026年1月1日
        String todayStr ="2026-01-01";
        switch (choice) {
            case "热销商品":
                queryWrapper.orderByDesc(Game::getSales).orderByAsc(Game::getId);
                break;
            case "新品上市":
                // 筛选 year < 今天，并按 year 升序排序（未来日期远的在前）
                queryWrapper.lt(Game::getYear, todayStr)
                        .orderByDesc(Game::getYear).orderByAsc(Game::getId);
                break;
            case "即将推出":
                // 筛选 year > 今天，并按 year 升序排序（未来日期远的在前）
                queryWrapper.gt(Game::getYear, todayStr)
                        .orderByAsc(Game::getYear);
                break;
            default:
                // 可选：处理未知 choice
                break;
        }

        // 执行分页查询
        Page<Game> resultPage = gameService.page(page,queryWrapper);
        return Result.success(resultPage);
    }
    //根据类型 整体查询
    @GetMapping("/queryCategory/{choice}/{pageNum}")
    public Result  queryCategory(@PathVariable String choice,@PathVariable Integer pageNum){
        int pageSize = 8;
        // 创建分页对象
        Page<Game> page = new Page<>(pageNum, pageSize);
        //创建查询条件
        LambdaQueryWrapper<Game> queryWrapper = new LambdaQueryWrapper<>();
        // 获取当前日期并格式化为 yyyy-MM-dd 字符串
        //  String todayStr = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        //假设 今天是 2026年1月1日
        String todayStr ="2026-01-01";
        switch (choice) {
            case "热门":
                queryWrapper.orderByDesc(Game::getSales).orderByAsc(Game::getId);
                break;
            case "新品":
                // 筛选 year < 今天，并按 year 升序排序（未来日期远的在前）
                queryWrapper.lt(Game::getYear, todayStr)
                        .orderByDesc(Game::getYear).orderByAsc(Game::getId);
                break;
            case "即将发售":
                // 筛选 year > 今天，并按 year 升序排序（未来日期远的在前）
                queryWrapper.gt(Game::getYear, todayStr)
                        .orderByAsc(Game::getYear);
                break;
            case "销量排序":
                if(isByDescA){
                    queryWrapper.orderByDesc(Game::getSales).orderByAsc(Game::getId);
                    isByDescA =false;
                }else{
                    queryWrapper.orderByAsc(Game::getSales).orderByAsc(Game::getId);
                    isByDescA =true;
                }
                break;
            case "价格排序":
                // 按照折扣价 price * discount 排序，然后按 id 升序（与原逻辑保持一致）
                if (isByDescB) {
                    queryWrapper.last("ORDER BY price * discount DESC, id ASC");
                    isByDescB =false;
                } else {
                    queryWrapper.last("ORDER BY price * discount ASC, id ASC");
                    isByDescB =true;
                }
                break;
            default:
                queryWrapper.eq(Game::getCategory, choice)
                        .orderByAsc(Game::getYear);
                break;
        }
        // 执行分页查询
        Page<Game> resultPage = gameService.page(page,queryWrapper);
        return Result.success(resultPage);
    }
}

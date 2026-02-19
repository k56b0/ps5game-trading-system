package com.zxy.ps5gametradingsystem.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.Game;
import com.zxy.ps5gametradingsystem.entity.Recycle;
import com.zxy.ps5gametradingsystem.service.GameService;
import com.zxy.ps5gametradingsystem.service.RecycleService;
import com.zxy.ps5gametradingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
version: version.value === 'guohang' ? '国行' : '非国行', // 假设1为国行，2为非国行，需根据实际映射
    language: language.value === 'ChineseSupport' ? '支持中文' : '不支持中文',
    appearance: appearance.value=== 'appearanceA' ? '光盘无划痕' : appearance.value === 'appearanceB' ? '光盘轻微划痕' : '光盘严重划痕',
    box: box.value === 'boxGood' ? '包装盒彩页齐全完好' : box.value === 'boxDamaged' ? '包装盒彩页齐全损坏' : '无包装盒',
 */
@RestController
@RequestMapping("/recycle")
public class RecycleController {
    @Autowired
    private RecycleService recycleService;
    @Autowired
    private GameService gameService;
    @PostMapping("/add")
    public Result add (@RequestBody Recycle s) {
        Double valuation = s.getValuation(); // 取出包装对象
        if(valuation == null || valuation == 0){
            LambdaQueryWrapper<Game> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Game::getGameName, s.getGameName());
            // 得到对应游戏
            Game g = gameService.getOne(queryWrapper);
            //当前价格
            double nowPrice =g.getPrice()*g.getDiscount();
            //版本，国行 便宜一点，非国行贵一点。
            //旧写法
            /*
            switch (s.getVersion()) {
                case "国行":
                    nowPrice=nowPrice*0.9;
                    break;
                case "非国行":
                    nowPrice=nowPrice*0.95;
                    break;
            }
             */
            nowPrice = switch (s.getVersion()) {
                case "国行" -> nowPrice * 0.9;
                case "非国行" -> nowPrice * 0.95;
                default -> nowPrice;
            };
            //语言 不支持中文便宜一点，支持中文贵一点。
            nowPrice = switch (s.getLanguage()) {
                case "不支持中文" -> nowPrice * 0.9;
                case "支持中文" -> nowPrice * 0.95;
                default -> nowPrice;
            };
            //划痕 光盘无划痕贵一点。
            nowPrice = switch (s.getLanguage()) {
                case "光盘严重划痕" -> nowPrice * 0.7;
                case "光盘轻微划痕" -> nowPrice * 0.9;
                case "光盘无划痕" -> nowPrice;
                default -> nowPrice;
            };
            //包装盒，有包装盒贵一点。
            nowPrice = switch (s.getLanguage()) {
                case "包装盒彩页齐全损坏" -> nowPrice * 0.8;
                case "无包装盒" -> nowPrice;
                case "包装盒彩页齐全完好" -> nowPrice;
                default -> nowPrice;
            };
            s.setValuation(nowPrice);
            return Result.success(s);
        }else{
            recycleService.save(s);
            return Result.success();
        }
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

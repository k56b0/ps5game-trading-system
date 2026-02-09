package com.zxy.ps5gametradingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`game`")
public class Game {
    private  String id;
    private  String englishName;
    private  String gameName;
    private  String price;
    private  String discount;
    private  String inventory;
    private  String category;
    private  String sales;
    private  String introduction;
    private  String year;
    private  String img;
}

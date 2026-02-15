package com.zxy.ps5gametradingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`game`")
public class Game {
    private  String id;
    private  String englishName;
    private  String gameName;
    private  Integer price;
    private  Float discount;
    private  Integer inventory;
    private  String category;
    private  Integer sales;
    private  String introduction;
    private  Integer year;
    private  String img;
}

package com.zxy.ps5gametradingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`shoppingcar`")
public class Shoppingcar {
    private  String id;
    private  String userId;
    private  String gameName;
    private  Integer quantity;
    private  Float price;
}

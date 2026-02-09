package com.zxy.ps5gametradingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`recharge_record`")
public class Recharge_record {
    private  String id;
    private  String userId;
    private  String time;
    private  Integer quantity;

}

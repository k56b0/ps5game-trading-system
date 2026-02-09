package com.zxy.ps5gametradingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("`order`")
public class Order {
    private String id;
    private String userId;
    private String orderTime;
    private String orderAddress;
    private Integer sum;
    // 该字段在数据库表中不存在，用于接收关联查询的结果
    @TableField(exist = false)
    private List<Order_details> orderDetails;
}

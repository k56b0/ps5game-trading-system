package com.zxy.ps5gametradingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`order_details`")
public class Order_details {
    private String id;
    private String orderId;
    private String gameName;
    private Integer price;
    private Integer quantity;


}

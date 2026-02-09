package com.zxy.ps5gametradingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`favorites`")
public class Favorites {
    private String id;
    private String userId;
    private String gameName;
}

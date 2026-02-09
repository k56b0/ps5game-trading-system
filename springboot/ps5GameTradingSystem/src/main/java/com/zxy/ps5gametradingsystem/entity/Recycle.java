package com.zxy.ps5gametradingsystem.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`recycle`")
public class Recycle {
    private  String id;
    private  String userId;
    private  String gameName;
    private  Integer valuation;
    private  String recyclingStatus;
    private  String version;
    private  String language;
    private  String appearance;
    private  String box;
}

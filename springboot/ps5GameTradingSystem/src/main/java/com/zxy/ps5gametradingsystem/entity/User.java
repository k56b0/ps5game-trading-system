package com.zxy.ps5gametradingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`user`")
public class User {
    private Long id;
    private String name;
    private String birth;
    private String address;
    private String email;
    private String passw;
    private Integer checkin;
    private String answer;
}
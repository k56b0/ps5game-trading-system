package com.zxy.ps5gametradingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`user`")
public class User {
    private String id;
    private String userName;
    private String gender;
    private String birth;
    private String password;
    private Integer checkIn;
    private String email;
    private String address;
    private String answer;
    private String checkTime;
    private Integer money;}
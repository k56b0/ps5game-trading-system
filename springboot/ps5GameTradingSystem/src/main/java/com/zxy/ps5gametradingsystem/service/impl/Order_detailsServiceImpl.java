package com.zxy.ps5gametradingsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.ps5gametradingsystem.entity.Order_details;
import com.zxy.ps5gametradingsystem.mapper.Order_detailsMapper;
import com.zxy.ps5gametradingsystem.service.Order_detailsService;
import org.springframework.stereotype.Service;

@Service
public class Order_detailsServiceImpl extends ServiceImpl<Order_detailsMapper, Order_details> implements Order_detailsService {
}

package com.zxy.ps5gametradingsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.ps5gametradingsystem.entity.Order;
import com.zxy.ps5gametradingsystem.entity.Order_details;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    // 主自定义查询，根据用户ID查询订单及其明细
    List<Order> selectOrderWithDetails(@Param("userId") String userId);
    // 主自定义查询加上分页，根据用户ID查询订单及其明细
    Page<Order> selectOrderPage(Page<Order> page, @Param("userId") String userId);

    // 子查询方法：必须定义在同一个Mapper接口中
    List<Order_details> selectOrderDetailsByOrderId(@Param("orderId") String orderId);


}

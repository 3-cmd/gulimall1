package com.cs.gulimall.order.dao;

import com.cs.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 11:18:11
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}

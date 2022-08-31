package com.cs.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.common.utils.PageUtils;
import com.cs.gulimall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 11:18:11
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


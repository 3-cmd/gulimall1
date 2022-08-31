package com.cs.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.common.utils.PageUtils;
import com.cs.gulimall.coupon.entity.SeckillPromotionEntity;

import java.util.Map;

/**
 * 秒杀活动
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 10:57:33
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


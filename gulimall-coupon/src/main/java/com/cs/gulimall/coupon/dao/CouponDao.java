package com.cs.gulimall.coupon.dao;

import com.cs.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 10:57:33
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}

package com.cs.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.common.utils.PageUtils;
import com.cs.gulimall.coupon.entity.HomeSubjectEntity;

import java.util.Map;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 10:57:33
 */
public interface HomeSubjectService extends IService<HomeSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


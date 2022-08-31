package com.cs.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.common.utils.PageUtils;
import com.cs.gulimall.member.entity.MemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 11:06:20
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


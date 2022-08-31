package com.cs.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.common.utils.PageUtils;
import com.cs.gulimall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 11:06:19
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


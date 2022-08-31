package com.cs.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.common.utils.PageUtils;
import com.cs.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 11:06:19
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


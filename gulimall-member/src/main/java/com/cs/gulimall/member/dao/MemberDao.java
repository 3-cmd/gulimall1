package com.cs.gulimall.member.dao;

import com.cs.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 11:06:19
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}

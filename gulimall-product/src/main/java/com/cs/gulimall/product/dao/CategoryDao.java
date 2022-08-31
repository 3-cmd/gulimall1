package com.cs.gulimall.product.dao;

import com.cs.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 09:44:54
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}

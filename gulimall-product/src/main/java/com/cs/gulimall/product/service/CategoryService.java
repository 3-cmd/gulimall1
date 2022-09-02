package com.cs.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.common.utils.PageUtils;
import com.cs.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 09:44:54
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();
}


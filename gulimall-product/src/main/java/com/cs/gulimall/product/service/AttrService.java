package com.cs.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.common.utils.PageUtils;
import com.cs.gulimall.product.entity.AttrEntity;
import com.cs.gulimall.product.vo.AttrResponseVo;
import com.cs.gulimall.product.vo.AttrVo;

import java.util.Map;

/**
 * 商品属性
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 09:44:54
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Long  catelogId, Map<String, Object> params);

    void save(AttrVo attr);

    AttrResponseVo getAttr(Long attrId);

    void updateAttr(AttrVo attr);
}


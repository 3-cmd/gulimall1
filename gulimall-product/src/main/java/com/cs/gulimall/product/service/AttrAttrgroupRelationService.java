package com.cs.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.common.utils.PageUtils;
import com.cs.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.cs.gulimall.product.entity.AttrEntity;
import com.cs.gulimall.product.vo.AttrAttrgroupRelationVo;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 09:44:54
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void removeRelation(AttrAttrgroupRelationVo[] relationVo);

    PageUtils getNoAttr(Long attrgroupId, Map<String, Object> params);
}


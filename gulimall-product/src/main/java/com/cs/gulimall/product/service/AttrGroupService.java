package com.cs.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.common.utils.PageUtils;
import com.cs.gulimall.product.entity.AttrEntity;
import com.cs.gulimall.product.entity.AttrGroupEntity;
import com.cs.gulimall.product.entity.CategoryEntity;
import com.cs.gulimall.product.vo.AttrAttrgroupRelationVo;
import com.cs.gulimall.product.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 09:44:54
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCatelogId(Map<String, Object> params, Long catelogId);

    /**
     * 查询到该条数据的完成路径,用于回显数据
     * @param CatLogId
     * @return
     */
    Long[] findCatelogPath(Long CatLogId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrs(Long catelogId);

    List<AttrEntity> getAttrGroupWithAttrsByAttrGropuId(Long attrgroupId);

}


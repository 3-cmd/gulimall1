package com.cs.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cs.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.cs.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.cs.gulimall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.common.utils.PageUtils;
import com.cs.common.utils.Query;

import com.cs.gulimall.product.dao.AttrDao;
import com.cs.gulimall.product.entity.AttrEntity;
import com.cs.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao relationDao;

    @Override
    public PageUtils queryPage(Long catelogId, Map<String, Object> params) {
        String key = (String) params.get("key");
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper.eq(catelogId != 0, AttrEntity::getCatelogId, catelogId)
                        .and(!key.isBlank(), obj -> {
                            obj.like(AttrEntity::getAttrId, key)
                                    .or().like(AttrEntity::getAttrName,key)
                                    .or().like(AttrEntity::getAttrType,key)
                                    .or().like(AttrEntity::getValueSelect,key);
                        })
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void save(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        //1.保存基本数据
        this.save(attrEntity);
        //2.保存关联关系
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
        relationDao.insert(attrAttrgroupRelationEntity);
    }

}
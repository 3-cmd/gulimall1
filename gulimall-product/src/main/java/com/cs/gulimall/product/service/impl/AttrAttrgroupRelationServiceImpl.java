package com.cs.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cs.gulimall.product.dao.AttrDao;
import com.cs.gulimall.product.entity.AttrEntity;
import com.cs.gulimall.product.vo.AttrAttrgroupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.common.utils.PageUtils;
import com.cs.common.utils.Query;

import com.cs.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.cs.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.cs.gulimall.product.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Autowired
    private AttrDao attrDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void removeRelation(AttrAttrgroupRelationVo[] relationVos) {
        List<AttrAttrgroupRelationEntity> collect = Arrays.stream(relationVos).map(Item -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(Item, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        attrAttrgroupRelationDao.deleteBatchRelation(collect);
    }

    @Override
    public PageUtils getNoAttr(Long attrgroupId, Map<String, Object> params) {
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(attrgroupId!=null,AttrAttrgroupRelationEntity::getAttrGroupId,attrgroupId);
        List<AttrAttrgroupRelationEntity> AttrAttrgroupRelationEntitys = this.list(wrapper);
        List<Long> attrIdList = AttrAttrgroupRelationEntitys.stream().map(attrAttrgroupRelationEntity -> {
            Long attrId = attrAttrgroupRelationEntity.getAttrId();
            return attrId;
        }).collect(Collectors.toList());
        if (attrIdList.size()==0){
            IPage<AttrEntity> page = new Query<AttrEntity>().getPage(params);
            List<AttrEntity> list = attrDao.selectList(null);
            page.setRecords(list);
            return new PageUtils(page);
        }
        //将改属性组已经关联的属性不在查询出来,只查询没有关联的属性
        List<AttrEntity> entityList=attrDao.notInAttrGroup(attrIdList);
        IPage<AttrEntity> page = new Query<AttrEntity>().getPage(params);
        page.setRecords(entityList);
        PageUtils pageUtils=new PageUtils(page);
        return pageUtils;
    }
}
package com.cs.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cs.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.cs.gulimall.product.dao.AttrGroupDao;
import com.cs.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.cs.gulimall.product.entity.AttrGroupEntity;
import com.cs.gulimall.product.entity.CategoryEntity;
import com.cs.gulimall.product.service.AttrGroupService;
import com.cs.gulimall.product.service.CategoryService;
import com.cs.gulimall.product.vo.AttrResponseVo;
import com.cs.gulimall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrGroupDao attrGroupDao;
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageUtils queryPageSale(Long catelogId, Map<String, Object> params) {
        String key = (String) params.get("key");
        if (key == null) {
            LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();
            IPage<AttrEntity> page = this.page(
                    new Query<AttrEntity>().getPage(params),
                    wrapper.eq(catelogId != 0, AttrEntity::getCatelogId, catelogId));
            List<AttrEntity> collect = page.getRecords().stream().filter(attrEntity -> attrEntity.getAttrType() == 0).collect(Collectors.toList());
            page.setRecords(collect);
            return new PageUtils(page);
        }
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper.eq(catelogId != 0, AttrEntity::getCatelogId, catelogId)
                        .and(!key.isBlank(), obj -> {
                            obj.like(AttrEntity::getAttrId, key)
                                    .or().like(AttrEntity::getAttrName, key)
                                    .or().like(AttrEntity::getAttrType, key)
                                    .or().like(AttrEntity::getValueSelect, key);
                        })
        );
        List<AttrEntity> collect1 = page.getRecords().stream().filter(attrEntity -> attrEntity.getAttrType() == 0).collect(Collectors.toList());
        page.setRecords(collect1);
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrResponseVo> collect = records.stream().map((attrEntity) -> {
            AttrResponseVo responseVo = new AttrResponseVo();
            BeanUtils.copyProperties(attrEntity, responseVo);
            //设置所属分类名称
            LambdaQueryWrapper<CategoryEntity> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(CategoryEntity::getCatId, attrEntity.getCatelogId());
            CategoryEntity category = categoryService.getOne(wrapper1);
            responseVo.setCatelogName(category.getName());
            //设置所属分组名称
            LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId());
            AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(wrapper2);
            if (relationEntity != null) {
                LambdaQueryWrapper<AttrGroupEntity> wrapper3 = new LambdaQueryWrapper<>();
                wrapper3.eq(AttrGroupEntity::getAttrGroupId, relationEntity.getAttrGroupId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectOne(wrapper3);
                responseVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
            return responseVo;
        }).collect(Collectors.toList());
        pageUtils.setList(collect);
        return pageUtils;
    }
    @Override
    public PageUtils queryPageBase(Long catelogId, Map<String, Object> params) {
        String key = (String) params.get("key");
        if (key == null) {
            LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();
            IPage<AttrEntity> page = this.page(
                    new Query<AttrEntity>().getPage(params),
                    wrapper.eq(catelogId != 0, AttrEntity::getCatelogId, catelogId));
            List<AttrEntity> collect = page.getRecords().stream().filter(attrEntity -> attrEntity.getAttrType() == 1).collect(Collectors.toList());
            page.setRecords(collect);
            return new PageUtils(page);
        }
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper.eq(catelogId != 0, AttrEntity::getCatelogId, catelogId)
                        .and(!key.isBlank(), obj -> {
                            obj.like(AttrEntity::getAttrId, key)
                                    .or().like(AttrEntity::getAttrName, key)
                                    .or().like(AttrEntity::getAttrType, key)
                                    .or().like(AttrEntity::getValueSelect, key);
                        })
        );
        List<AttrEntity> collect1 = page.getRecords().stream().filter(attrEntity -> attrEntity.getAttrType() == 1).collect(Collectors.toList());
        page.setRecords(collect1);
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrResponseVo> collect = records.stream().map((attrEntity) -> {
            AttrResponseVo responseVo = new AttrResponseVo();
            BeanUtils.copyProperties(attrEntity, responseVo);
            //设置所属分类名称
            LambdaQueryWrapper<CategoryEntity> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(CategoryEntity::getCatId, attrEntity.getCatelogId());
            CategoryEntity category = categoryService.getOne(wrapper1);
            responseVo.setCatelogName(category.getName());
            //设置所属分组名称
            LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId());
            AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(wrapper2);
            if (relationEntity != null) {
                LambdaQueryWrapper<AttrGroupEntity> wrapper3 = new LambdaQueryWrapper<>();
                wrapper3.eq(AttrGroupEntity::getAttrGroupId, relationEntity.getAttrGroupId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectOne(wrapper3);
                responseVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
            return responseVo;
        }).collect(Collectors.toList());
        pageUtils.setList(collect);
        return pageUtils;
    }
    @Override
    @Transactional
    public void save(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        //1.保存基本数据
        this.save(attrEntity);
        //2.保存关联关系
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
        relationDao.insert(attrAttrgroupRelationEntity);
    }

    @Override
    public AttrResponseVo getAttr(Long attrId) {
        //设置所属分组名称
        AttrResponseVo responseVo = new AttrResponseVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity,responseVo);
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> relationWrapper=new LambdaQueryWrapper<>();
        relationWrapper.eq(AttrAttrgroupRelationEntity::getAttrId,attrId);
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = relationDao.selectOne(relationWrapper);
        if (attrAttrgroupRelationEntity!=null) {
            LambdaQueryWrapper<AttrGroupEntity> attrGroupEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            attrGroupEntityLambdaQueryWrapper.eq(AttrGroupEntity::getAttrGroupId, attrAttrgroupRelationEntity.getAttrGroupId());
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectOne(attrGroupEntityLambdaQueryWrapper);
            responseVo.setAttrGroupId(attrGroupEntity.getAttrGroupId());
        }
        //设置回显路径
        Long[] catelogPath = attrGroupService.findCatelogPath(attrEntity.getCatelogId());
        responseVo.setCatelogPath(catelogPath);
        return responseVo;
    }

    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity=new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);
        //修改分组关联
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(AttrAttrgroupRelationEntity::getAttrId,attr.getAttrId());
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        relationDao.update(attrAttrgroupRelationEntity,wrapper);

    }



}
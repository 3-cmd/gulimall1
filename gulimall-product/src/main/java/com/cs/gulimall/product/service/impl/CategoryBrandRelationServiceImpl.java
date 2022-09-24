package com.cs.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cs.gulimall.product.dao.BrandDao;
import com.cs.gulimall.product.dao.CategoryDao;
import com.cs.gulimall.product.entity.BrandEntity;
import com.cs.gulimall.product.entity.CategoryEntity;
import com.cs.gulimall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.common.utils.PageUtils;
import com.cs.common.utils.Query;

import com.cs.gulimall.product.dao.CategoryBrandRelationDao;
import com.cs.gulimall.product.entity.CategoryBrandRelationEntity;
import com.cs.gulimall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private BrandDao brandDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryBrandRelationDao categoryBrandRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetails(CategoryBrandRelationEntity categoryBrandRelation) {
        //根据id获取到名称即可
        //根据brandId获取到对应的brand一个实体
        LambdaQueryWrapper<BrandEntity> brandWrapper=new LambdaQueryWrapper<>();
        brandWrapper.eq(BrandEntity::getBrandId,categoryBrandRelation.getBrandId());
        BrandEntity brandEntity = brandDao.selectOne(brandWrapper);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        //根据catelogId获取到catelogName
        LambdaQueryWrapper<CategoryEntity> categoryWrapper=new LambdaQueryWrapper<>();
        categoryWrapper.eq(CategoryEntity::getCatId,categoryBrandRelation.getCatelogId());
        CategoryEntity categoryEntity = categoryDao.selectOne(categoryWrapper);
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        categoryBrandRelationDao.insert(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity categoryBrandRelation=new CategoryBrandRelationEntity();
        categoryBrandRelation.setBrandId(brandId);
        categoryBrandRelation.setBrandName(name);
        LambdaQueryWrapper<CategoryBrandRelationEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getBrandId,brandId);
        categoryBrandRelationDao.update(categoryBrandRelation,wrapper);
    }

    @Override
    public void updateCategory(Long catId, String name) {
        CategoryBrandRelationEntity categoryBrandRelation=new CategoryBrandRelationEntity();
        categoryBrandRelation.setCatelogId(catId);
        categoryBrandRelation.setCatelogName(name);
        LambdaQueryWrapper<CategoryBrandRelationEntity> wrapper=new LambdaQueryWrapper<>();
        categoryBrandRelationDao.update(categoryBrandRelation,wrapper.eq(CategoryBrandRelationEntity::getCatelogId,catId));
    }

}
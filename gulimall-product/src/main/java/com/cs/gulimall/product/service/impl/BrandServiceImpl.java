package com.cs.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cs.gulimall.product.service.CategoryBrandRelationService;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.common.utils.PageUtils;
import com.cs.common.utils.Query;

import com.cs.gulimall.product.dao.BrandDao;
import com.cs.gulimall.product.entity.BrandEntity;
import com.cs.gulimall.product.service.BrandService;


@Service("brandService")
@Slf4j
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        log.info("params:{}",params);
        //添加模糊搜索的条件
        LambdaQueryWrapper<BrandEntity> wrapper=new LambdaQueryWrapper<>();
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper.eq(!params.get("key").toString().isBlank(),BrandEntity::getFirstLetter,params.get("key"))
        );

        return new PageUtils(page);
    }

    @Override
    public void updateShowStatus(BrandEntity brand) {
        brandDao.updateById(brand);
    }

    @Override
    public void updateDetails(BrandEntity brand) {
        this.updateById(brand);
        //如果更新名称,证明brand对象中的name不为空,那么进行更新其他表的操作
        if (!Strings.isBlank(brand.getName())){
            //同步更新其他表中的数据
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());
        }

    }

}
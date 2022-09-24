package com.cs.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cs.gulimall.product.entity.CategoryBrandRelationEntity;
import com.cs.gulimall.product.service.CategoryBrandRelationService;
import com.cs.common.utils.PageUtils;
import com.cs.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 10:10:40
 */
@RestController
@RequestMapping("product/categorybrandrelation")
@Slf4j
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);
        return R.ok().put("page", page);
    }
    @GetMapping("/catelog/list")
    public R getRelationList(@RequestParam Long brandId){
        LambdaQueryWrapper<CategoryBrandRelationEntity> wrapper=new LambdaQueryWrapper<>();
        List<CategoryBrandRelationEntity> list = categoryBrandRelationService.list(wrapper.eq(CategoryBrandRelationEntity::getBrandId, brandId));
        return R.ok().put("data",list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
        //获取到的数据为branId与catelogId,数据库中还应该存入brand_name与catelog_name,查询出来然后存储到数据库中
		categoryBrandRelationService.saveDetails(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

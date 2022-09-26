package com.cs.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cs.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.cs.gulimall.product.entity.AttrEntity;
import com.cs.gulimall.product.entity.CategoryEntity;
import com.cs.gulimall.product.service.AttrAttrgroupRelationService;
import com.cs.gulimall.product.vo.AttrAttrgroupRelationVo;
import com.cs.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cs.gulimall.product.entity.AttrGroupEntity;
import com.cs.gulimall.product.service.AttrGroupService;
import com.cs.common.utils.PageUtils;
import com.cs.common.utils.R;



/**
 * 属性分组
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 10:10:40
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@PathVariable("catelogId") Long catelogId , @RequestParam Map<String, Object> params){
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPageByCatelogId(params,catelogId);
        return R.ok().put("page", page);
    }
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> list=attrGroupService.getAttrGroupWithAttrsByAttrGropuId(attrgroupId);
        return R.ok().put("data",list);

    }
    /**
     * 查询全部的信息
     * @param
     * @return
     */
    @GetMapping("{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId){
        //1.查出当前分类下的所有属性分组
        //2.查出每个属性分组的所有属性
        List<AttrGroupWithAttrsVo> list = attrGroupService.getAttrGroupWithAttrs(catelogId);
        return R.ok().put("data",list);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        //根据groupId查询到该条数据的完成的id,就是找到他的爸爸id  爷爷id,直到上面无父类
        Long[] catelogPath = attrGroupService.findCatelogPath(attrGroup.getCatelogId());
        attrGroup.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }
    ///product/attrgroup/attr/relation/delete
    //移除关联关系
    @RequestMapping("/attr/relation/delete")
    public R removeRelation(@RequestBody AttrAttrgroupRelationVo[] relationVos){
        attrAttrgroupRelationService.removeRelation(relationVos);
        return R.ok();
    }
    //将给属性分组没有关联的属性展示出来,已经关联的则不进行展示
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R getAttrGroupNoAttr(@PathVariable("attrgroupId") Long attrgroupId,@RequestParam Map<String,Object> params){
        PageUtils pageUtils=attrAttrgroupRelationService.getNoAttr(attrgroupId,params);
        return R.ok().put("page",pageUtils);
    }
}

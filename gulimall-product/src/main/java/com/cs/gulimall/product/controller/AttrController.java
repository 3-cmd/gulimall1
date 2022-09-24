package com.cs.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.cs.gulimall.product.vo.AttrResponseVo;
import com.cs.gulimall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cs.gulimall.product.entity.AttrEntity;
import com.cs.gulimall.product.service.AttrService;
import com.cs.common.utils.PageUtils;
import com.cs.common.utils.R;



/**
 * 商品属性
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 10:10:40
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 列表
     */
//    @RequestMapping("/list")
//    //@RequiresPermissions("product:attr:list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = attrService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }
    @RequestMapping("/base/list/{catelogId}")
    //@RequiresPermissions("product:attr:list")
    public R listByPage(@PathVariable("catelogId") Long catelogId, @RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(catelogId,params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
		AttrResponseVo responseVo=attrService.getAttr(attrId);
        return R.ok().put("attr", responseVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr){
		attrService.save(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}

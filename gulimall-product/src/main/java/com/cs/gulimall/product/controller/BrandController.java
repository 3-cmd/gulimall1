package com.cs.gulimall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.cs.valid.AddGroup;
import com.cs.valid.UpdateGroup;
import com.cs.valid.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cs.gulimall.product.entity.BrandEntity;
import com.cs.gulimall.product.service.BrandService;
import com.cs.common.utils.PageUtils;
import com.cs.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 10:10:40
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public R save(@Validated(value = {AddGroup.class}) @RequestBody BrandEntity brand /*, BindingResult result*/) throws Exception{
//        if (result.hasErrors()) {
//            Map<String, Object> map = new HashMap<>();
//            //1.获取校验的错误结果
//            result.getFieldErrors().forEach(item -> {
//                String message = item.getDefaultMessage();
//                String field = item.getField();
//                map.put(field, message);
//            });
//            return R.error(400, "提交的数据不合法").put("data", map);
//        } else {
//        }
        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@Validated(value = {UpdateGroup.class}) @RequestBody BrandEntity brand) {
        //我们在更新brand表的同时,也应该将使用到该表中信息进行更新,例如在categoryBranRelation中使用到了brand中的数据
        //所以在修改brand表的数据的同时,也要将它也改掉
        brandService.updateDetails(brand);
        return R.ok("修改成功");
    }
    /**
     * 修改状态
     */
    @RequestMapping("/updateStatus")
    //@RequiresPermissions("product:brand:update")
    public R updateStatus(@Validated(value = {UpdateStatusGroup.class}) @RequestBody BrandEntity brand) {
        brandService.updateShowStatus(brand);
        return R.ok("修改成功");
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}

package com.cs.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cs.gulimall.product.entity.BrandEntity;
import com.cs.gulimall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = GulimallProductApplication.class)
@RunWith(SpringRunner.class)
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("华为");
        brandService.save(brandEntity);
        System.out.println("保存成功");
    }

    @Test
    public void test2() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(1L);
        brandEntity.setName("苹果");
        brandEntity.setDescript("苹果手机");
        brandService.updateById(brandEntity);
    }

    @Test
    public void test3() {
        LambdaQueryWrapper<BrandEntity> wrapper = new LambdaQueryWrapper<>();
        List<BrandEntity> list = brandService.list(wrapper.eq(BrandEntity::getBrandId, 1L));
        list.forEach((item) -> {
            System.out.println(item);
        });
    }


}

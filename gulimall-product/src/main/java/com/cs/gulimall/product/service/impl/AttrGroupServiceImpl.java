package com.cs.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cs.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.cs.gulimall.product.dao.AttrDao;
import com.cs.gulimall.product.dao.CategoryDao;
import com.cs.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.cs.gulimall.product.entity.AttrEntity;
import com.cs.gulimall.product.entity.CategoryEntity;
import com.cs.gulimall.product.service.AttrService;
import com.cs.gulimall.product.vo.AttrAttrgroupRelationVo;
import com.cs.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.common.utils.PageUtils;
import com.cs.common.utils.Query;

import com.cs.gulimall.product.dao.AttrGroupDao;
import com.cs.gulimall.product.entity.AttrGroupEntity;
import com.cs.gulimall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrGroupDao attrGroupDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private AttrDao attrDao;
    @Autowired
    private AttrService attrService;
    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCatelogId(Map<String, Object> params, Long catelogId) {
        /**
         * {
         *    page: 1,//当前页码
         *    limit: 10,//每页记录数
         *    sidx: 'id',//排序字段
         *    order: 'asc/desc',//排序方式
         *    key: '华为'//检索关键字
         * }
         */
        //模糊匹配  进行key的查询
        String key = (String) params.get("key");
        if (key == null) {
            LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(catelogId != 0, AttrGroupEntity::getCatelogId, catelogId);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
        //当查询全部时,catlogID会变为0,此时,不再需要第一个条件
        wrapper.eq(catelogId != 0, AttrGroupEntity::getCatelogId, catelogId)
                .and(!key.isBlank(),(obj) -> {
                    obj
                            .like(AttrGroupEntity::getSort, Long.parseLong(key))
                            .or().like(AttrGroupEntity::getAttrGroupName, key)
                            .or().like(AttrGroupEntity::getAttrGroupId, Long.parseLong(key))
                            .or().like(AttrGroupEntity::getDescript, key)
                            .or().like(AttrGroupEntity::getCatelogId, Long.parseLong(key))
                            .or().like(AttrGroupEntity::getIcon, key);
                });
        //使用自定义分装的工具类,将map中的数据取出来 page与limit取出来,如果不存在则默认为 page:1 limit:10
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
        //PageUtils是将page对象进行了分解并且分别赋值,因为数据在page对象中的各个不同的属性中,向分页查询到的数据存在page.getRecords()
        //我们将此进行赋值给我们熟知的例如list变量
        return new PageUtils(page);
    }


    @Override
    public Long[] findCatelogPath(Long CatLogId) {
        LambdaQueryWrapper<CategoryEntity> wrapper = new LambdaQueryWrapper<>();
        CategoryEntity category = categoryDao.selectOne(wrapper.eq(CategoryEntity::getCatId, CatLogId));
        List<Long> paths = new ArrayList<>();
        paths.add(category.getCatId());
        List<Long> list = find(category.getParentCid(), paths);
//        System.out.println(list);
        Collections.reverse(list);//将数据进行反向排列

        return (Long[]) list.toArray(new Long[list.size()]);
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrs(Long catelogId) {
        LambdaQueryWrapper<AttrGroupEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(AttrGroupEntity::getCatelogId,catelogId);
        List<AttrGroupEntity> attrGroupEntities = attrGroupDao.selectList(wrapper);
        List<AttrGroupWithAttrsVo> attrGroupWithAttrs= attrGroupEntities.stream().map(attrGroupEntity -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(attrGroupEntity, attrGroupWithAttrsVo);

            List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = attrAttrgroupRelationDao.selectList(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupEntity.getAttrGroupId()));
            List<AttrEntity> collect = attrAttrgroupRelationEntities.stream().map(attrAttrgroupRelation -> {
                LambdaQueryWrapper<AttrEntity> attrEntityWrapper = new LambdaQueryWrapper<>();
                attrEntityWrapper.eq(AttrEntity::getAttrId, attrAttrgroupRelation.getAttrId());
                AttrEntity attrEntity = attrDao.selectOne(attrEntityWrapper);
                return attrEntity;
            }).collect(Collectors.toList());
            attrGroupWithAttrsVo.setAttrs(collect);
            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());
        return attrGroupWithAttrs;
    }



    /**
     * 根据传递过来的 父id 查询到该 父id作为catId的一条数据,然后递归查询处每条数据封装到list集合中,这个递归方法是找到一条数据以及其的上一代
     *
     * @param parentCid
     * @return
     */
    public List<Long> find(Long parentCid, List<Long> paths) {
        if (parentCid != 0) {
            LambdaQueryWrapper<CategoryEntity> wrapper = new LambdaQueryWrapper<>();
            CategoryEntity category = categoryDao.selectOne(wrapper.eq(CategoryEntity::getCatId, parentCid));
            paths.add(category.getCatId());
            find(category.getParentCid(), paths);
        }
        return paths;
    }
    @Override
    public List<AttrEntity> getAttrGroupWithAttrsByAttrGropuId(Long attrgroupId) {
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(AttrAttrgroupRelationEntity::getAttrGroupId,attrgroupId);
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = attrAttrgroupRelationDao.selectList(wrapper);
        List<Long> collect = attrAttrgroupRelationEntities.stream().map(attrAttrgroupRelationEntity -> {
            Long attrId = attrAttrgroupRelationEntity.getAttrId();
            return attrId;
        }).collect(Collectors.toList());
        List<AttrEntity> attrEntities = attrService.listByIds(collect);
        return attrEntities;
    }


}
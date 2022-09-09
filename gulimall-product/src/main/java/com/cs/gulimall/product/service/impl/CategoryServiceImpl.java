package com.cs.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cs.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.common.utils.PageUtils;
import com.cs.common.utils.Query;

import com.cs.gulimall.product.dao.CategoryDao;
import com.cs.gulimall.product.entity.CategoryEntity;
import com.cs.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        //1.查询出所有分类
        List<CategoryEntity> categoryEntityList = categoryDao.selectList(null);
        //2.组装成树形的父子结构
        //2.1 找到所有一级分类
        List<CategoryEntity> level1Menus = categoryEntityList.stream().filter(categoryEntity -> {
            //第一级分类的父id为0
            return categoryEntity.getParentCid() == 0;
        }).map(menu->{
            menu.setChildren(getChildren(menu,categoryEntityList));
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        //2.2 为每一个分类设置他的子分类

        return level1Menus;
    }
    /**
     * 这个方法是将所有数据的进行分级
     *
     * @param root
     * @param all
     * @return
     */
    private List<CategoryEntity> getChildren(CategoryEntity root,List<CategoryEntity> all){
        //root代表每一级分类,初始为第一级分类
        //获取到所有分类的数据,每一条数据都存在id与parent_id属性,当这两个属性
        //相等时,将parent_id相等的那一条数据设置为id相等的那条数据的儿子就可以
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map(categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return children;

    }

    /**
     * 批量删除菜单选项,并且当该菜单存在子节点时不能删除
     * 传入的参数为多项数据的id值
     * @param idList
     */
    @Override
    public void removeMenusByIds(List<Long> idList) {
        // TODO
        // 1.这里先自行定义,将存在子类的数据设置为无法删除
        //将idList进行遍历,通过每一个id查询其有无作为parent_cid的数据,如果该id作为parent_cid查询出数据,说明该id对应的数据存在子类,那么将这个
           //数据过滤出来进行收集成一个集合,该集合中所有的元素代表着存在子类的元素,如果元素个数大于0个,代表我们选中的数据存在子类无法删除
        //filter()代表着满足条件则进行收集
//        List<Long> ResultList = idList.stream()
//                .filter(id->categoryDao.selectList(new LambdaQueryWrapper<CategoryEntity>().eq(CategoryEntity::getParentCid,id)).size()!=0)
//                .collect(Collectors.toList());
//        if (ResultList.size()>0){
//            return "error";
//        }
//        categoryDao.deleteBatchIds(idList);
//        return "success";
        categoryDao.deleteBatchIds(idList);
    }


}
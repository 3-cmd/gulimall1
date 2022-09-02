package com.cs.gulimall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return children;

    }
}
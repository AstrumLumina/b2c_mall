package com.wzw.b2cmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.product.pojo.dto.PmsCategoryDto;
import com.wzw.b2cmall.product.pojo.entity.PmsCategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 12:25:59
 */
public interface PmsCategoryService extends IService<PmsCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //以树形结构放回所有的分类
    List<PmsCategoryDto> queryCategoryTree();

   //以树形结构 返回指定分类下（空则为最顶层） 指定深度（空则是全部）的分类
    List<PmsCategoryDto> queryCategoryTree(Long catParentId ,Integer deepth);

    //以树形结构返回指定层级间的分类
    List<PmsCategoryDto> queryCategoryTreeInDeepth(Integer startDeepth,Integer endDeepth) ;

    Integer updateCategoryByTrees(List<PmsCategoryDto> pmsCategoryDtos);


    Boolean updateCategoryDtoListNoTree(List<PmsCategoryDto> pmsCategoryDtos);
}


package com.wzw.b2cmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.b2cmall.product.pojo.vo.PmsCategoryVo;
import com.wzw.b2cmall.common.utils.PageUtils;
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

   /**
    * @Description: 以树形结构放回所有的分类
    * @Param
    * @return java.util.List<com.wzw.b2cmall.b2cmallproduct.pojo.vo.PmsCategoryVo>
    * date 2023/8/12 23:19
    * @author WangZhiWen
    * @Version: 1.0
    * @since JDK 11
    * @Company: 版权所有
    **/
    List<PmsCategoryVo> queryCategoryTree();

    /**
     * @Description:  以树形结构 返回指定分类下（空则为最顶层） 指定深度（空则是全部）的分类
     * @Param com.wzw.b2cmall.b2cmallproduct.pojo.vo.PmsCategoryVo pmsCategoryVo
     * @Param java.lang.Integer deepth
     * @return java.util.List<com.wzw.b2cmall.b2cmallproduct.pojo.vo.PmsCategoryVo>
     * date 2023/8/12 23:20
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    List<PmsCategoryVo> queryCategoryTree(Long catParentId ,Integer deepth);

    /**
     * @Description: 以树形结构返回指定层级间的分类
     * @Param java.lang.Integer startDeepth
     * @Param java.lang.Integer endDeepth
     * @return java.util.List<com.wzw.b2cmall.b2cmallproduct.pojo.vo.PmsCategoryVo>
     * date 2023/8/12 23:20
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    List<PmsCategoryVo> queryCategoryTreeInDeepth(Integer startDeepth,Integer endDeepth) ;

    int updateCategoryByTrees(List<PmsCategoryVo> pmsCategoryVos);
}


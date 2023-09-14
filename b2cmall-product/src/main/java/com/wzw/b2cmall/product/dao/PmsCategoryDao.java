package com.wzw.b2cmall.product.dao;

import com.wzw.b2cmall.product.pojo.dto.PmsCategoryDto;
import com.wzw.b2cmall.product.pojo.entity.PmsCategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品三级分类
 * 
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 12:25:59
 */
@Mapper
public interface PmsCategoryDao extends BaseMapper<PmsCategoryEntity> {

    /**
     * @Description: 以链表即非树形的方式查询所有的商品分类
     * @Param
     * @return java.util.List<com.wzw.b2cmall.product.pojo.dto.PmsCategoryDto>
     * date 2023/9/12 18:10
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    List<PmsCategoryDto> selectCatDtoListNotTree();

    /**
     * @Description: 查询指定类下是所有子分类, 只查询一级
     * @Param java.lang.Long parentCid
     * @return java.util.List<com.wzw.b2cmall.product.pojo.dto.PmsCategoryDto>
     * date 2023/9/12 18:10
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    List<PmsCategoryDto> selectCatDtoDirectChildren(@Param("parentCid") Long parentCid);

    /**
     * @Description: mybatis collection实现的树形结构查询
     * @Param java.lang.Long parentCid
     * @return java.util.List<com.wzw.b2cmall.product.pojo.dto.PmsCategoryDto>
     * date 2023/9/12 18:10
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    List<PmsCategoryDto> selectCatDtoTree(@Param("parentCid") Long parentCid);

   /**
    * @Description: 查询指定层级的所有分类
    * @Param java.lang.Integer deepth
    * @return java.util.List<com.wzw.b2cmall.product.pojo.dto.PmsCategoryDto>
    * date 2023/9/12 18:17
    * @author WangZhiWen
    * @Version: 1.0
    * @since JDK 11
    * @Company: 版权所有
    **/
    List<PmsCategoryDto> selectCatDtosInDeepth(@Param("deepth") Integer deepth);

    Integer updateCatDto(@Param("pmsCategoryDto")PmsCategoryDto pmsCategoryDto);


}

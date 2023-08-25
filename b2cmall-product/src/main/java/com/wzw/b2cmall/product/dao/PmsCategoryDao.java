package com.wzw.b2cmall.product.dao;

import com.wzw.b2cmall.product.pojo.vo.PmsCategoryVo;
import com.wzw.b2cmall.product.pojo.entity.PmsCategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
     * @return java.util.List<com.wzw.b2cmall.b2cmallproduct.pojo.vo.PmsCategoryVo>
     * date 2023/8/12 22:50
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    List<PmsCategoryVo> selectListNotTree();

    /**
     * @Description: 查询指定类下是所有子分类, 只查询一级
     * @Param java.lang.Long parentCid
     * @return java.util.List<com.wzw.b2cmall.b2cmallproduct.pojo.vo.PmsCategoryVo>
     * date 2023/8/12 22:10
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    List<PmsCategoryVo> selectDirectChildren(@Param("parentCid") Long parentCid);

    /**
     * @Description: mybatis collection实现的树形结构查询
     * @Param java.lang.Long parentCid: null默认为 0级
     * @return java.util.List<com.wzw.b2cmall.b2cmallproduct.pojo.vo.PmsCategoryVo>
     * date 2023/8/12 22:09
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    List<PmsCategoryVo> selectCatTree(@Param("parentCid") Long parentCid);

    /**
     * @Description: 查询指定层级的所有分类
     * @Param java.lang.Integer deepth: null默认为 0级
     * @return java.util.List<com.wzw.b2cmall.b2cmallproduct.pojo.vo.PmsCategoryVo>
     * date 2023/8/12 22:11
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    List<PmsCategoryVo> selectCatsInDeepth(@Param("deepth") Integer deepth);

}

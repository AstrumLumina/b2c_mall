package com.wzw.b2cmall.product.dao;

import com.wzw.b2cmall.product.pojo.entity.PmsBrandEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌
 * 
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 12:25:59
 */
@Mapper
public interface PmsBrandDao extends BaseMapper<PmsBrandEntity> {
    int insertAll(@Param("pmsBrandEntityList") List<PmsBrandEntity> pmsBrandEntityList);
    // List<PmsBrandEntity>

    List<PmsBrandEntity> selectAll();
}

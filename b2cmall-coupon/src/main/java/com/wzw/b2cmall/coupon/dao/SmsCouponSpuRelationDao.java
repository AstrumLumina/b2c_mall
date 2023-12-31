package com.wzw.b2cmall.coupon.dao;

import com.wzw.b2cmall.coupon.pojo.entity.SmsCouponSpuRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券与产品关联
 * 
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 13:36:53
 */
@Mapper
public interface SmsCouponSpuRelationDao extends BaseMapper<SmsCouponSpuRelationEntity> {
	
}

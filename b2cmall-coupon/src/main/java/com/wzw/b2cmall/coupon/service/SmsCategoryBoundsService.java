package com.wzw.b2cmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.coupon.pojo.entity.SmsCategoryBoundsEntity;

import java.util.Map;

/**
 * 商品分类积分设置
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 13:36:53
 */
public interface SmsCategoryBoundsService extends IService<SmsCategoryBoundsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


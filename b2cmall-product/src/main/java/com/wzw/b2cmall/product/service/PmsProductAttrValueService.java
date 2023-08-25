package com.wzw.b2cmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.product.pojo.entity.PmsProductAttrValueEntity;

import java.util.Map;

/**
 * spu属性值
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 12:25:59
 */
public interface PmsProductAttrValueService extends IService<PmsProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


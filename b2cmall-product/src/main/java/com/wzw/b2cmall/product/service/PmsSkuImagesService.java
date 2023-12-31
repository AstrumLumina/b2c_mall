package com.wzw.b2cmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.product.pojo.entity.PmsSkuImagesEntity;

import java.util.Map;

/**
 * sku图片
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 12:26:00
 */
public interface PmsSkuImagesService extends IService<PmsSkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


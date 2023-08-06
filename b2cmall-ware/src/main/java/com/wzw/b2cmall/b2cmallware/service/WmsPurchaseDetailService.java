package com.wzw.b2cmall.b2cmallware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.common.utils.PageUtils;
import com.wzw.b2cmall.b2cmallware.entity.WmsPurchaseDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 13:41:02
 */
public interface WmsPurchaseDetailService extends IService<WmsPurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


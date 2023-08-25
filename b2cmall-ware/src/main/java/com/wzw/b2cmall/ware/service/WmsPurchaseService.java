package com.wzw.b2cmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.ware.pojo.entity.WmsPurchaseEntity;

import java.util.Map;

/**
 * 采购信息
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 13:41:02
 */
public interface WmsPurchaseService extends IService<WmsPurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


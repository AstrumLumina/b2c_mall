package com.wzw.b2cmall.b2cmallorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.common.utils.PageUtils;
import com.wzw.b2cmall.b2cmallorder.entity.OmsOrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 13:39:29
 */
public interface OmsOrderService extends IService<OmsOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

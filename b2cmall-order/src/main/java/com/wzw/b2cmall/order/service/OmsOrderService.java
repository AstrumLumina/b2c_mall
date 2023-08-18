package com.wzw.b2cmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.order.pojo.entity.OmsOrderEntity;

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


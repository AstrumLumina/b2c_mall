package com.wzw.b2cmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.coupon.pojo.entity.SmsHomeAdvEntity;

import java.util.Map;

/**
 * 首页轮播广告
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 13:36:52
 */
public interface SmsHomeAdvService extends IService<SmsHomeAdvEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


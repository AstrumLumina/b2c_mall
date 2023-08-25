package com.wzw.b2cmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.member.pojo.entity.UmsMemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 13:28:09
 */
public interface UmsMemberStatisticsInfoService extends IService<UmsMemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


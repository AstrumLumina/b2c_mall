package com.wzw.b2cmall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.common.utils.Query;

import com.wzw.b2cmall.order.dao.OmsRefundInfoDao;
import com.wzw.b2cmall.order.pojo.entity.OmsRefundInfoEntity;
import com.wzw.b2cmall.order.service.OmsRefundInfoService;


@Service("omsRefundInfoService")
public class OmsRefundInfoServiceImpl extends ServiceImpl<OmsRefundInfoDao, OmsRefundInfoEntity> implements OmsRefundInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OmsRefundInfoEntity> page = this.page(
                new Query<OmsRefundInfoEntity>().getPage(params),
                new QueryWrapper<OmsRefundInfoEntity>()
        );

        return new PageUtils(page);
    }

}
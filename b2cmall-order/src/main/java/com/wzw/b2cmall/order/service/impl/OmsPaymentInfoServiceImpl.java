package com.wzw.b2cmall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.common.utils.Query;

import com.wzw.b2cmall.order.dao.OmsPaymentInfoDao;
import com.wzw.b2cmall.order.pojo.entity.OmsPaymentInfoEntity;
import com.wzw.b2cmall.order.service.OmsPaymentInfoService;


@Service("omsPaymentInfoService")
public class OmsPaymentInfoServiceImpl extends ServiceImpl<OmsPaymentInfoDao, OmsPaymentInfoEntity> implements OmsPaymentInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OmsPaymentInfoEntity> page = this.page(
                new Query<OmsPaymentInfoEntity>().getPage(params),
                new QueryWrapper<OmsPaymentInfoEntity>()
        );

        return new PageUtils(page);
    }

}
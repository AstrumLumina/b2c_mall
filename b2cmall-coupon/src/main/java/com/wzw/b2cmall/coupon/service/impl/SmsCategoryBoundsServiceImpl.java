package com.wzw.b2cmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.common.utils.Query;

import com.wzw.b2cmall.coupon.dao.SmsCategoryBoundsDao;
import com.wzw.b2cmall.coupon.pojo.entity.SmsCategoryBoundsEntity;
import com.wzw.b2cmall.coupon.service.SmsCategoryBoundsService;


@Service("smsCategoryBoundsService")
public class SmsCategoryBoundsServiceImpl extends ServiceImpl<SmsCategoryBoundsDao, SmsCategoryBoundsEntity> implements SmsCategoryBoundsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SmsCategoryBoundsEntity> page = this.page(
                new Query<SmsCategoryBoundsEntity>().getPage(params),
                new QueryWrapper<SmsCategoryBoundsEntity>()
        );

        return new PageUtils(page);
    }

}
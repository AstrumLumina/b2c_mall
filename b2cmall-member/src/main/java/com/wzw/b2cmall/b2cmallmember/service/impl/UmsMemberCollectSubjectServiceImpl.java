package com.wzw.b2cmall.b2cmallmember.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.common.utils.PageUtils;
import com.wzw.common.utils.Query;

import com.wzw.b2cmall.b2cmallmember.dao.UmsMemberCollectSubjectDao;
import com.wzw.b2cmall.b2cmallmember.entity.UmsMemberCollectSubjectEntity;
import com.wzw.b2cmall.b2cmallmember.service.UmsMemberCollectSubjectService;


@Service("umsMemberCollectSubjectService")
public class UmsMemberCollectSubjectServiceImpl extends ServiceImpl<UmsMemberCollectSubjectDao, UmsMemberCollectSubjectEntity> implements UmsMemberCollectSubjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UmsMemberCollectSubjectEntity> page = this.page(
                new Query<UmsMemberCollectSubjectEntity>().getPage(params),
                new QueryWrapper<UmsMemberCollectSubjectEntity>()
        );

        return new PageUtils(page);
    }

}
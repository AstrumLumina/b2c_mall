package com.wzw.b2cmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.member.pojo.entity.UmsMemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 13:28:09
 */
public interface UmsMemberService extends IService<UmsMemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


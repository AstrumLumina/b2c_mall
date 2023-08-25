package com.wzw.b2cmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wzw.b2cmall.member.pojo.entity.UmsMemberLoginLogEntity;
import com.wzw.b2cmall.member.service.UmsMemberLoginLogService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.common.utils.R;

//import org.apache.shiro.authz.annotation.RequiresPermissions;



/**
 * 会员登录记录
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-15 11:11:10
 */
@RestController
@RequestMapping("/umsmemberloginlog")
public class UmsMemberLoginLogController {
    @Autowired
    private UmsMemberLoginLogService umsMemberLoginLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("b2cmallmember:umsmemberloginlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = umsMemberLoginLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("b2cmallmember:umsmemberloginlog:info")
    public R info(@PathVariable("id") Long id){
		UmsMemberLoginLogEntity umsMemberLoginLog = umsMemberLoginLogService.getById(id);

        return R.ok().put("umsMemberLoginLog", umsMemberLoginLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("b2cmallmember:umsmemberloginlog:save")
    public R save(@RequestBody UmsMemberLoginLogEntity umsMemberLoginLog){
		umsMemberLoginLogService.save(umsMemberLoginLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("b2cmallmember:umsmemberloginlog:update")
    public R update(@RequestBody UmsMemberLoginLogEntity umsMemberLoginLog){
		umsMemberLoginLogService.updateById(umsMemberLoginLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("b2cmallmember:umsmemberloginlog:delete")
    public R delete(@RequestBody Long[] ids){
		umsMemberLoginLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

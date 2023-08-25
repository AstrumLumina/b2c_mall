package com.wzw.b2cmall.ware.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wzw.b2cmall.ware.pojo.entity.WmsWareInfoEntity;
import com.wzw.b2cmall.ware.service.WmsWareInfoService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.common.utils.R;

//import org.apache.shiro.authz.annotation.RequiresPermissions;



/**
 * 仓库信息
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-15 11:09:11
 */
@RestController
@RequestMapping("/wmswareinfo")
public class WmsWareInfoController {
    @Autowired
    private WmsWareInfoService wmsWareInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("b2cmallware:wmswareinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wmsWareInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("b2cmallware:wmswareinfo:info")
    public R info(@PathVariable("id") Long id){
		WmsWareInfoEntity wmsWareInfo = wmsWareInfoService.getById(id);

        return R.ok().put("wmsWareInfo", wmsWareInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("b2cmallware:wmswareinfo:save")
    public R save(@RequestBody WmsWareInfoEntity wmsWareInfo){
		wmsWareInfoService.save(wmsWareInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("b2cmallware:wmswareinfo:update")
    public R update(@RequestBody WmsWareInfoEntity wmsWareInfo){
		wmsWareInfoService.updateById(wmsWareInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("b2cmallware:wmswareinfo:delete")
    public R delete(@RequestBody Long[] ids){
		wmsWareInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

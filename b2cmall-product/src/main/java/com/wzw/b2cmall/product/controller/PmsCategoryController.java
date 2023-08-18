package com.wzw.b2cmall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wzw.b2cmall.common.utils.Constant;
import com.wzw.b2cmall.product.pojo.vo.PmsCategoryVo;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wzw.b2cmall.product.pojo.entity.PmsCategoryEntity;
import com.wzw.b2cmall.product.service.PmsCategoryService;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.common.utils.R;

//import org.apache.shiro.authz.annotation.RequiresPermissions;



/**
 * 商品三级分类
 *
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-15 11:03:35
 */
@RestController
@RequestMapping("/pmscategory")
public class PmsCategoryController {
    @Autowired
    private PmsCategoryService pmsCategoryService;

    /**
     * 树状商品分类
     */
    @GetMapping({  "/tree" , "/tree/{catParentId}" , "/tree/{catParentId}/{deepth}" })
    public R<List<PmsCategoryVo>> getCategoryTreeByPid(@ApiParam(required = false) @PathVariable(value = "catParentId",required = false) Long catParentId,
                                                       @ApiParam(required = false) @PathVariable(value = "deepth",required = false) Integer deepth){

        if (catParentId==null&&deepth==null){
            return R.ok().setData(pmsCategoryService.queryCategoryTree());
        }else {
            if (validateCategoryParamDeepthHasError(deepth)){
                return R.error("startDeepth greater than endDeepth,or out range");
            }
            return R.ok().setData(pmsCategoryService.queryCategoryTree(catParentId, deepth));
        }
    }

    /**
     * 查找指定层级的商品分类
     */
    @GetMapping({   "/deepth/{startDeepth}/{endDeepth}" })
    public R getCategoryInDeepth(@ApiParam(value = "startDeepth",required = false) @PathVariable(value = "startDeepth",required = false) Integer startDeepth,
                                 @ApiParam( value = "endDeepth",required = false) @PathVariable(value = "endDeepth",required = false)  Integer endDeepth){

        if (startDeepth>endDeepth||validateCategoryParamDeepthHasError(startDeepth,endDeepth)){
            return R.error("startDeepth greater than endDeepth,or out range");
        }
        return R.ok().setData(pmsCategoryService.queryCategoryTreeInDeepth(startDeepth,endDeepth));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("b2cmallproduct:pmscategory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmsCategoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("b2cmallproduct:pmscategory:info")
    public R info(@PathVariable("catId") Long catId){
        PmsCategoryEntity pmsCategory = pmsCategoryService.getById(catId);

        return R.ok().put("pmsCategory", pmsCategory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("b2cmallproduct:pmscategory:save")
    public R save(@RequestBody PmsCategoryEntity pmsCategory){
        pmsCategoryService.save(pmsCategory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("b2cmallproduct:pmscategory:update")
    public R update(@RequestBody PmsCategoryEntity pmsCategory){
        pmsCategoryService.updateById(pmsCategory);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("b2cmallproduct:pmscategory:delete")
    public R delete(@RequestBody Long[] catIds){
        pmsCategoryService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

    private boolean validateCategoryParamDeepthHasError(Integer... deepths){
        for (Integer deepth : deepths) {
            if (deepth==null)
                continue;
            if (deepth<0||deepth> Constant.MAX_PMS_CATEGORY_LEVEL){
                return true;
            }
        }
        return false;
    }
}

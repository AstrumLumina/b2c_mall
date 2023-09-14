package com.wzw.b2cmall.resource.controller;


import com.wzw.b2cmall.common.utils.Constant;
import com.wzw.b2cmall.common.utils.R;
import com.wzw.b2cmall.minio.utils.MinioBucketTemplate;
import com.wzw.b2cmall.minio.utils.MyMinioClientUtil;
import com.wzw.b2cmall.product.pojo.dto.PmsCategoryDto;
import com.wzw.b2cmall.resource.feign.PmsCategoryService;
import com.wzw.b2cmall.resource.service.ProductIconOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Map;

@RestController
@RequestMapping("/product/icon")
public class ProductMinioController {
    @Autowired
    private ProductIconOssService productIconOssService;


    @GetMapping("/presigndownload")
    public R<String> getDownloadUrl(@RequestParam  String objectName){
        return R.ok().setData(productIconOssService.getPresignedGetIconUrl(objectName));
    }
    @GetMapping("/presignputupload")
    public R<String> getPutUploadUrl(@RequestParam  String objectName){
        return R.ok().setData(productIconOssService.getPresignedPutIconUrl(objectName));
    }

    @GetMapping("/presignputupload/byiconurl")
    public R<String> getUpdatedCatIocnPutUploadUrl(@RequestParam("icon") String iconUrl){
        return R.ok().setData(productIconOssService.getPresignedPutIocnUrlForExitedIcon(iconUrl));
    }
    @GetMapping("/presignpostupload/byiconurl")
    public R getUpdatedCatIocnPostFormdata(@RequestParam("icon") String iconUrl){
        return R.ok().setData(productIconOssService.getPresignedPostIocnUrlForExitedIcon(iconUrl));
    }

    @GetMapping("/presignputupload/fixed/{catId}")
    //TODO 需要防止高并发下的数据不一致性,不过该接口不太可能有高并发
    public R<String> getCatIocnFixedPutUploadUrl(@PathVariable @Min(1L) Long catId){
        return R.ok().setData(productIconOssService.getPresignedPutIocnUrlForNewIcon(catId));

    }
    @GetMapping("/presignpostupload/fixed/{catId}")
    public R getCatIocnFixedPostFormdata(@PathVariable @Min(1L) Long catId){
        return R.ok().setData(productIconOssService.getPresignedPostIocnUrlForNewIcon(catId));
    }

}

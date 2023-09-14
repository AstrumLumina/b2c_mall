package com.wzw.b2cmall.resource.service;


import com.wzw.b2cmall.common.utils.Constant;
import com.wzw.b2cmall.common.utils.R;
import com.wzw.b2cmall.minio.utils.MyMinioClientUtil;
import com.wzw.b2cmall.product.pojo.dto.PmsCategoryDto;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import java.util.Map;

public interface ProductIconOssService {

    String getPresignedGetIconUrl(String objectName);

    String getPresignedPutIconUrl(String objectName);

    /**
     * 有些存储服务不支持post方式,默认抛出异常
     **/
    default Map<String,String> getPresignedPostIocnUrl( String objectName){
        throw new RuntimeException(this.getClass().getName()+" unsupport the method of [String getPresignedPostIocnUrl(String objectName)]");
    }

    default Map<String,String>getPresignedPostIocnUrlForExitedIcon( String iconUrl){
        throw new RuntimeException(this.getClass().getName()+" unsupport the method of [Map<String,String>getPresignedPostIocnUrlForExitedIcon( String iconUrl)]");
    }

    String getPresignedPutIocnUrlForExitedIcon(String iconUrl);

    /**
     * @Description: 需要保证幂等性(防止重复上传图标,浪费空间),不过该接口不大可能出现并发操作
     **/
    String getPresignedPutIocnUrlForNewIcon(Long catId);

    /**
     * @Description: 需要保证幂等性(防止重复上传图标,浪费空间),不过该接口不大可能出现并发操作
     **/
    default Map<String,String>getPresignedPostIocnUrlForNewIcon(Long catId){
        throw new RuntimeException(this.getClass().getName()+" unsupport the method of [Map<String,String>getPresignedPostIocnUrlForNewIcon(Long catId)]");
    }


}

package com.wzw.b2cmall.resource.service.impl;

import com.wzw.b2cmall.common.utils.Constant;
import com.wzw.b2cmall.common.utils.R;
import com.wzw.b2cmall.minio.autoconfig.MinioConfigProperties;
import com.wzw.b2cmall.minio.utils.MinioBucketTemplate;
import com.wzw.b2cmall.minio.utils.MyMinioClientUtil;
import com.wzw.b2cmall.minio.utils.MyUrlUtil;
import com.wzw.b2cmall.product.pojo.dto.PmsCategoryDto;
import com.wzw.b2cmall.resource.feign.PmsCategoryService;
import com.wzw.b2cmall.resource.service.ProductIconOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class ProductIconOssServerMinioImpl implements ProductIconOssService {
    @Autowired
    private MinioBucketTemplate productCategoryIconMinioBucketTemplate;
    @Autowired
    private PmsCategoryService pmsCategoryService;
    @Autowired
    private MinioConfigProperties minioConfigProperties;

    @Override
    public String getPresignedGetIconUrl(String objectName) {
       return productCategoryIconMinioBucketTemplate.getPresignedDownloadObjectUrl(objectName);
    }

    @Override
    public String getPresignedPutIconUrl(String objectName) {
        return productCategoryIconMinioBucketTemplate.getPresignedPutUploadObjectUrl(objectName);
    }

    @Override
    public Map<String, String> getPresignedPostIocnUrl(String objectName) {
        Map<String, String> presignedPostFormData = productCategoryIconMinioBucketTemplate.getPresignedPostFormData(objectName);
        presignedPostFormData.put("key", objectName);
        return presignedPostFormData;
    }

    @Override
    public Map<String, String> getPresignedPostIocnUrlForExitedIcon(String iconUrl) {
        String objectNameFromUrl = MyUrlUtil.getObjectNameFromUrl(iconUrl);
        return this.getPresignedPostIocnUrl(objectNameFromUrl);
    }

    @Override
    public String getPresignedPutIocnUrlForExitedIcon(String iconUrl) {
        String objectNameFromUrl = MyUrlUtil.getObjectNameFromUrl(iconUrl);
        return this.getPresignedPutIconUrl(objectNameFromUrl);
    }

    @Override
    public String getPresignedPutIocnUrlForNewIcon(Long catId) {
        R<PmsCategoryDto> info = pmsCategoryService.getInfo(catId);
        if (info.getCode()!=0){
            throw new RuntimeException("feign to get pmsCategoryInfo occured error: "+info.getMsg());
        }
        PmsCategoryDto data = info.getData();
        String iconObjectName=null;
        if (data.getIcon()!=null){
            iconObjectName=MyUrlUtil.getObjectNameFromRelaPathOrRelaUrl(data.getIcon());
        }
        if (!StringUtils.hasLength(iconObjectName)){
            iconObjectName =productCategoryIconMinioBucketTemplate.generateObjectNameForFile(data.getName());
        }
        String iconUrl = productCategoryIconMinioBucketTemplate.getPresignedPutUploadObjectUrl(iconObjectName);
        PmsCategoryDto catDtoToUpdate = new PmsCategoryDto();
        catDtoToUpdate.setCatId(catId);
        catDtoToUpdate.setIcon(MyUrlUtil.getUrlPathFromUrl(iconUrl));
        pmsCategoryService.updatePmscat(catDtoToUpdate);
        return iconUrl;
    }

    @Override
    public Map<String, String> getPresignedPostIocnUrlForNewIcon(Long catId) {
        R<PmsCategoryDto> info = pmsCategoryService.getInfo(catId);
        if (info.getCode()!=0){
            throw new RuntimeException("feign to get pmsCategoryInfo occured error: "+info.getMsg());
        }
        PmsCategoryDto data = info.getData();
        String iconObjectName=MyUrlUtil.getObjectNameFromRelaPathOrRelaUrl(data.getIcon());;
        if (!StringUtils.hasLength(iconObjectName)){
            iconObjectName =productCategoryIconMinioBucketTemplate.generateObjectNameForFile(data.getName());
        }
        Map<String, String> presignedPostFormData = productCategoryIconMinioBucketTemplate.getPresignedPostFormData(iconObjectName);
        presignedPostFormData.put("key", iconObjectName);
        String tempUrl = productCategoryIconMinioBucketTemplate.getMinioServerUrl()+ "/" + productCategoryIconMinioBucketTemplate.getBucketName();
        presignedPostFormData.put(Constant.MINIO_POST_UPLOAD_URL,tempUrl);

        tempUrl+=("/"+iconObjectName);
        PmsCategoryDto catDtoToUpdate = new PmsCategoryDto();
        catDtoToUpdate.setCatId(catId);
        catDtoToUpdate.setIcon(tempUrl);
        pmsCategoryService.updatePmscat(catDtoToUpdate);

        return presignedPostFormData;
    }
}

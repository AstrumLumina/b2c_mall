package com.wzw.b2cmall.product.pojo.dto;


import com.wzw.b2cmall.common.utils.MyBeanCopyUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Accessors(chain = true)  cglib的BeanCopier 会查早返回为void的 setter,链式方式会导致属性复制全为null
@AllArgsConstructor
@NoArgsConstructor
public class PmsCategoryDto {
    /**
     * 分类id
     */
    private Long catId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 父分类id
     */
    private Long parentCid;
    /**
     * 层级
     */
    private Integer catLevel;
    /**
     * 是否显示[0-不显示，1显示]
     */
    private Integer showStatus;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 图标地址
     */
    private String icon;
    /**
     * 计量单位
     */
    private String productUnit;
    /**
     * 商品数量
     */
    private Integer productCount;

    public PmsCategoryDto(Object o) {
        MyBeanCopyUtil.defaultBeanCopy(o,this);
    }
    public <T> PmsCategoryDto(Class<T>  tClass,T o) {
        this.convertToCategoryDto(tClass,o);
    }
    public <T> PmsCategoryDto convertToCategoryDto(Class<T>  tClass,T o) {
        MyBeanCopyUtil.defaultBeanCopy(tClass,PmsCategoryDto.class,o,this);
        return this;
    }
}

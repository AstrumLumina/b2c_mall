package com.wzw.b2cmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.wzw.b2cmall.product.pojo.vo.PmsCategoryVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品三级分类
 * 
 * @author wzw
 * @email 325884@whut.edu.cn
 * @date 2023-08-03 12:25:59
 */
@Data
@Accessors(chain = true)
@TableName("pms_category")
@ApiModel
public class PmsCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
	@TableId
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

    //这里还是希望与数据库保持一致
    //如果实际需要使用到的类与 entity有细微区别，使用pojo包下的
  /*  *//*
    * 该分类下的子分类
    * *//*
    @TableField(exist = false)
    private List<PmsCategoryEntity> childCats;*/
}

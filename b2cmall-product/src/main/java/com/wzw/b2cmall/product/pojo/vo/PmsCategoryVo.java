package com.wzw.b2cmall.product.pojo.vo;

import com.wzw.b2cmall.product.pojo.entity.PmsAttrEntity;
import com.wzw.b2cmall.product.pojo.entity.PmsCategoryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.GenericArrayType;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(parent = PmsCategoryEntity.class)
public class PmsCategoryVo extends PmsCategoryEntity {
    @ApiModelProperty(value = "所有的子分类树: PmsCateGoryVo[]")//不要指定type,否则swagger无法正确展示元素类型
    private List<PmsCategoryVo> childCats;

    public PmsCategoryVo(PmsCategoryEntity pmsCategoryEntity) {
        // 实现对象将属性复制
        BeanCopier beanCopier = BeanCopier.create(PmsCategoryEntity.class, PmsCategoryVo.class, false);
        beanCopier.copy(pmsCategoryEntity, this, null);
    }

//    //原来该类放到 bo 模型中,可以有一定的业务逻辑,但该类是需要直接返回到
//    //前端的,使用 bo 模型不太好,且该业务逻辑使用到的可能性较小,通过controller和service就直接调用了相关逻辑了
//    public void flushChildCats(){
//        PmsCategoryDao pmsCategoryDao = SpringContextUtils.getBean(PmsCategoryDao.class);
//        //查询该分类的子类
//    }
//
//    public List<PmsCategoryPojo> getChildCats() {
//        if (childCats==null)
//        {
//            flushChildCats();
//        }
//        return childCats;
//    }
}

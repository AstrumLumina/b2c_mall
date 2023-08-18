package com.wzw.b2cmall.product.service.impl;

import com.wzw.b2cmall.product.pojo.vo.PmsCategoryVo;
import com.wzw.b2cmall.common.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.b2cmall.common.utils.PageUtils;
import com.wzw.b2cmall.common.utils.Query;

import com.wzw.b2cmall.product.dao.PmsCategoryDao;
import com.wzw.b2cmall.product.pojo.entity.PmsCategoryEntity;
import com.wzw.b2cmall.product.service.PmsCategoryService;


@Service("pmsCategoryService")
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryDao, PmsCategoryEntity> implements PmsCategoryService {

    @Autowired
    PmsCategoryDao pmsCategoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsCategoryEntity> page = this.page(
                new Query<PmsCategoryEntity>().getPage(params),
                new QueryWrapper<PmsCategoryEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public List<PmsCategoryVo> queryCategoryTree() {

//        //方案一
//        return pmsCategoryDao.selectCatTree(0L);

//        //方案二
//        List<PmsCategoryVo> pmsCategoryVoList = pmsCategoryDao.selectListNotTree();
//        List<PmsCategoryVo> pmsCategoryVoTree = pmsCategoryVoList
//                .stream()
//                .filter(pmsCategoryVo -> pmsCategoryVo.getCatLevel() == 0)
//                .map(pmsCategoryVo -> {
//                    pmsCategoryVo.setChildCats(getPmsCategoryVoByParentId(pmsCategoryVoList, pmsCategoryVo.getCatId()));
//                    return pmsCategoryVo;
//                })
//                .sorted((categoryVo1, categoryVo2) -> (categoryVo1.getSort() == null ? 0 : categoryVo1.getSort()) - (categoryVo2.getSort() == null ? 0 : categoryVo2.getSort()))
//                .collect(Collectors.toList());
//        return pmsCategoryVoTree;


        //方案三
        return getPmsCategoryVoByParentId(null,0L,Constant.MAX_PMS_CATEGORY_LEVEL);
    }

    @Override
    public List<PmsCategoryVo> queryCategoryTree(Long catParentId ,Integer deepth){
        if (deepth==null||deepth<1||deepth>Constant.MAX_PMS_CATEGORY_LEVEL){
            return Collections.emptyList();
        }
        if (catParentId==null){
            catParentId=0L;
        }


//        //方案一
//        return getPmsCategoryVoByParentId(null,catParentId,deepth);

        //方案二
        List<PmsCategoryVo> pmsCategoryVoTree = pmsCategoryDao.selectDirectChildren(catParentId);
        List<PmsCategoryVo> pmsCategoryVoTreeBottomLayer=pmsCategoryVoTree;
        while (--deepth>0){
            pmsCategoryVoTreeBottomLayer.forEach(
                    innerCatVo->innerCatVo.setChildCats(pmsCategoryDao.selectDirectChildren(innerCatVo.getCatId()))
            );
        }
        return pmsCategoryVoTree;
    }

    @Override
    public List<PmsCategoryVo> queryCategoryTreeInDeepth(Integer startDeepth, Integer endDeepth)  {
        if (startDeepth<1||startDeepth>Constant.MAX_PMS_CATEGORY_LEVEL||endDeepth<1||endDeepth>Constant.MAX_PMS_CATEGORY_LEVEL||startDeepth>endDeepth){
            return Collections.emptyList();
        }

        List<PmsCategoryVo> pmsCategoryVoTree = pmsCategoryDao.selectCatsInDeepth(startDeepth);
        Deque<List<PmsCategoryVo>> pmsCategoryVoListDeque=null;

        endDeepth-=startDeepth;
        while (--endDeepth>0){
            if (pmsCategoryVoListDeque==null){
                pmsCategoryVoListDeque=new LinkedList<>();
                pmsCategoryVoListDeque.add(pmsCategoryVoTree);
            }
            int count=pmsCategoryVoListDeque.size();
            while (count-- > 0){
                for (PmsCategoryVo pmsCategoryVo : pmsCategoryVoListDeque.peekFirst()) {
                    List<PmsCategoryVo> children = pmsCategoryDao.selectDirectChildren(pmsCategoryVo.getCatId());
                    if (children.size()!=0){
                        pmsCategoryVoListDeque.addLast(children);
                    }
                    pmsCategoryVo.setChildCats(children);
                }
            }
        }
        return pmsCategoryVoTree;
    }

    private List<PmsCategoryVo> getPmsCategoryVoByParentId(List<PmsCategoryVo> pmsCategoryVoList,Long parentId,int restDeepth){
        if (restDeepth<=0){
            return null;
        }
        if (pmsCategoryVoList==null) {
            pmsCategoryVoList=pmsCategoryDao.selectListNotTree();
        }
        List<PmsCategoryVo> finalPmsCategoryVoList = pmsCategoryVoList;
        return pmsCategoryVoList
                .stream()
                .filter(categoryVo -> categoryVo.getParentCid() == parentId)
                .map(pmsCategoryVo->{
                    if (pmsCategoryVo.getCatLevel()<= Constant.MAX_PMS_CATEGORY_LEVEL)
                        pmsCategoryVo.setChildCats(getPmsCategoryVoByParentId(finalPmsCategoryVoList,pmsCategoryVo.getCatId(),restDeepth-1));
                    return pmsCategoryVo;
                })
                .sorted((categoryVo1,categoryVo2)-> (categoryVo1.getSort()==null?0:categoryVo1.getSort()) - (categoryVo2.getSort()==null?0:categoryVo2.getSort()))
                .collect(Collectors.toList());
    }

//    //方案二
//    private List<PmsCategoryVo> getPmsCategoryVoByParentId(List<PmsCategoryVo> pmsCategoryVoList,Long parentId){
//             return pmsCategoryVoList
//                .stream()
//                .filter(categoryVo -> categoryVo.getParentCid() == parentId)
//                .map(pmsCategoryVo->{
//                    if (pmsCategoryVo.getCatLevel()<= Constant.MAX_PMS_CATEGORY_LEVEL)
//                        pmsCategoryVo.setChildCats(getPmsCategoryVoByParentId(pmsCategoryVoList,pmsCategoryVo.getCatId()));
//                    return pmsCategoryVo;
//                })
//                .sorted((categoryVo1,categoryVo2)-> (categoryVo1.getSort()==null?0:categoryVo1.getSort()) - (categoryVo2.getSort()==null?0:categoryVo2.getSort()))
//                .collect(Collectors.toList());
//    }
}


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
                for (PmsCategoryVo pmsCategoryVo : pmsCategoryVoListDeque.pollFirst()) {
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

    @Override
    public int updateCategoryByTrees(List<PmsCategoryVo> pmsCategoryVos) {
        if (pmsCategoryVos==null){
            return 0;
        }
        //广度优先遍历更新
        Deque<List<PmsCategoryVo>> listDeque=new LinkedList<>();
        listDeque.add(pmsCategoryVos);
        List<PmsCategoryEntity> pmsCategoryEntityList=new ArrayList<>();
        int updatedCount=0;
        while (listDeque.size()>0){
            List<PmsCategoryVo> categoryVos = listDeque.pollFirst();
            categoryVos.removeIf(Objects::isNull);//提前过来空元素
            categoryVos.forEach(pmsCategoryVo -> {
                if (pmsCategoryVo!=null&&pmsCategoryVo.getChildCats()!=null){
                    listDeque.addLast(pmsCategoryVo.getChildCats());
                }
            });
            if (categoryVos.size()>0){
                pmsCategoryEntityList.clear();
                pmsCategoryEntityList.addAll(categoryVos);
                //pmsCategoryEntityList.removeIf(Objects::isNull);//过滤掉空元素,防止报错 为防止跑异常,上移过滤操作
                if (updateBatchById(pmsCategoryEntityList)){//当list元素个数为0时返回 false ,会导致无关报错 要提前过过滤掉集合个数为0的情况
                    updatedCount+=pmsCategoryEntityList.size();
                }else {
                    throw new RuntimeException("update batch pmsCategoryEntity fail");
                }
            }
        }
        return updatedCount;
    }

    private List<PmsCategoryVo> getPmsCategoryVoByParentId(List<PmsCategoryVo> pmsCategoryVoList,Long parentId,int restDeepth){
        if (restDeepth<=0){
            return null;
        }
        if (pmsCategoryVoList==null) {
            pmsCategoryVoList=pmsCategoryDao.selectListNotTree();
        }
        List<PmsCategoryVo> finalPmsCategoryVoList = pmsCategoryVoList;
        List<PmsCategoryVo> collect = pmsCategoryVoList
                .stream()
                //.filter(categoryVo -> categoryVo.getParentCid() == parentId) //封装类型必须使用equal
                .filter(categoryVo -> categoryVo.getParentCid().equals(parentId))
                .map(pmsCategoryVo -> {
                    if (pmsCategoryVo.getName().equals("test2")){
                        System.out.println("enter test2");
                    }
                    if (pmsCategoryVo.getCatId()==1426L){
                        log.debug("enter error category");
                    }
                    if (pmsCategoryVo.getCatLevel() < Constant.MAX_PMS_CATEGORY_LEVEL)
                        pmsCategoryVo.setChildCats(getPmsCategoryVoByParentId(finalPmsCategoryVoList, pmsCategoryVo.getCatId(), restDeepth - 1));
                    return pmsCategoryVo;
                })
                .sorted((categoryVo1, categoryVo2) -> (categoryVo1.getSort() == null ? 0 : categoryVo1.getSort()) - (categoryVo2.getSort() == null ? 0 : categoryVo2.getSort()))
                .collect(Collectors.toList());
        return collect;
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


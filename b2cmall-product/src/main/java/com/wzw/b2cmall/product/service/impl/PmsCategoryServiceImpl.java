package com.wzw.b2cmall.product.service.impl;

import com.wzw.b2cmall.common.utils.Constant;
import com.wzw.b2cmall.product.pojo.dto.PmsCategoryDto;
import io.swagger.models.auth.In;
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
    public List<PmsCategoryDto> queryCategoryTree() {

//        //方案一
//        return pmsCategoryDao.selectCatDtoTree(0L);

        //方案二
        List<PmsCategoryDto> pmsCategoryDtoList = pmsCategoryDao.selectCatDtoListNotTree();
        List<PmsCategoryDto> pmsCategoryDtoTree = pmsCategoryDtoList
                .stream()
                .filter(pmsCategoryDto -> pmsCategoryDto!=null&&(new Long(0L).equals(pmsCategoryDto.getParentCid()) ))
                .map(pmsCategoryDto -> {
                    pmsCategoryDto.setChildCats(getPmsCategoryDtoByParentId(pmsCategoryDtoList, pmsCategoryDto.getCatId()));
                    return pmsCategoryDto;
                })
                .sorted((categoryDto1, categoryDto2) -> (categoryDto1.getSort() == null ? 0 : categoryDto1.getSort()) - (categoryDto2.getSort() == null ? 0 : categoryDto2.getSort()))
                .collect(Collectors.toList());
        return pmsCategoryDtoTree;

//        //方案三
//        return getPmsCategoryDtoByParentId(null,0L,Constant.MAX_PMS_CATEGORY_LEVEL);
    }

    @Override
    public List<PmsCategoryDto> queryCategoryTree(Long catParentId ,Integer deepth){
        if (deepth==null||deepth<1||deepth>Constant.MAX_PMS_CATEGORY_LEVEL){
            return Collections.emptyList();
        }
        if (catParentId==null){
            catParentId=0L;
        }


//        //方案一
//        return getPmsCategoryDtoByParentId(null,catParentId,deepth);

        //方案二
        List<PmsCategoryDto> pmsCategoryDtoTree = pmsCategoryDao.selectCatDtoDirectChildren(catParentId);
        List<PmsCategoryDto> pmsCategoryDtoTreeBottomLayer=pmsCategoryDtoTree;
        while (--deepth>0){
            pmsCategoryDtoTreeBottomLayer.forEach(
                    innerCatDto->innerCatDto.setChildCats(pmsCategoryDao.selectCatDtoDirectChildren(innerCatDto.getCatId()))
            );
        }
        return pmsCategoryDtoTree;
    }

    @Override
    public List<PmsCategoryDto> queryCategoryTreeInDeepth(Integer startDeepth, Integer endDeepth)  {
        if (startDeepth<1||startDeepth>Constant.MAX_PMS_CATEGORY_LEVEL||endDeepth<1||endDeepth>Constant.MAX_PMS_CATEGORY_LEVEL||startDeepth>endDeepth){
            return Collections.emptyList();
        }

        List<PmsCategoryDto> pmsCategoryDtoTree = pmsCategoryDao.selectCatDtosInDeepth(startDeepth);
        Deque<List<PmsCategoryDto>> pmsCategoryVoListDeque=null;

        endDeepth-=startDeepth;
        while (--endDeepth>0){
            if (pmsCategoryVoListDeque==null){
                pmsCategoryVoListDeque=new LinkedList<>();
                pmsCategoryVoListDeque.add(pmsCategoryDtoTree);
            }
            int count=pmsCategoryVoListDeque.size();
            while (count-- > 0){
                for (PmsCategoryDto pmsCategoryVo : pmsCategoryVoListDeque.pollFirst()) {
                    List<PmsCategoryDto> children = pmsCategoryDao.selectCatDtoDirectChildren(pmsCategoryVo.getCatId());
                    if (children.size()!=0){
                        pmsCategoryVoListDeque.addLast(children);
                    }
                    pmsCategoryVo.setChildCats(children);
                }
            }
        }
        return pmsCategoryDtoTree;
    }

    @Override
    public Integer updateCategoryByTrees(List<PmsCategoryDto> pmsCategoryDtos) {
        if (pmsCategoryDtos==null){
            return 0;
        }
        //广度优先遍历更新
        Deque<List<PmsCategoryDto>> listDeque=new LinkedList<>();
        listDeque.add(pmsCategoryDtos);
        List<PmsCategoryDto> pmsCategoryDtoList=new ArrayList<>();
        int updatedCount=0;
        while (listDeque.size()>0){
            List<PmsCategoryDto> categoryDtos = listDeque.pollFirst();
            categoryDtos.removeIf(Objects::isNull);//提前过来空元素
            categoryDtos.forEach(pmsCategoryDto -> {
                if (pmsCategoryDto!=null&&pmsCategoryDto.getChildCats()!=null){
                    listDeque.addLast(pmsCategoryDto.getChildCats());
                }
            });
            if (categoryDtos.size()>0){
                pmsCategoryDtoList.clear();
                pmsCategoryDtoList.addAll(categoryDtos);
                //pmsCategoryEntityList.removeIf(Objects::isNull);//过滤掉空元素,防止报错 为防止跑异常,上移过滤操作
                if (updateCategoryDtoListNoTree(pmsCategoryDtoList)){//当list元素个数为0时返回 false ,会导致无关报错 要提前过过滤掉集合个数为0的情况
                    updatedCount+=pmsCategoryDtoList.size();
                }else {
                    throw new RuntimeException("update batch pmsCategoryEntity fail");
                }
            }
        }
        return updatedCount;
    }

    @Override
    public Boolean updateCategoryDtoListNoTree(List<PmsCategoryDto> pmsCategoryDtos) {
        pmsCategoryDtos.forEach((pmsCategoryDto ->{
            pmsCategoryDao.updateCatDto(pmsCategoryDto);
        }));
        return Boolean.TRUE;
    }

    private List<PmsCategoryDto> getPmsCategoryDtoByParentId(List<PmsCategoryDto> pmsCategoryDtoList, Long parentId, int restDeepth){
        if (restDeepth<=0){
            return null;
        }
        if (pmsCategoryDtoList==null) {
            pmsCategoryDtoList=pmsCategoryDao.selectCatDtoListNotTree();
        }
        List<PmsCategoryDto> finalPmsCategoryDtoList = pmsCategoryDtoList;
        List<PmsCategoryDto> collect = pmsCategoryDtoList
                .stream()
                .filter(categoryDto -> categoryDto.getParentCid() == parentId) //封装类型必须使用equal
                .filter(categoryDto -> categoryDto.getParentCid().equals(parentId))
                .map(pmsCategoryDto -> {
                    if (pmsCategoryDto.getName().equals("test2")){
                        System.out.println("enter test2");
                    }
                    if (pmsCategoryDto.getCatId()==1426L){
                        log.debug("enter error category");
                    }
                    if (pmsCategoryDto.getCatLevel() < Constant.MAX_PMS_CATEGORY_LEVEL)
                        pmsCategoryDto.setChildCats(getPmsCategoryDtoByParentId(finalPmsCategoryDtoList, pmsCategoryDto.getCatId(), restDeepth - 1));
                    return pmsCategoryDto;
                })
                .sorted((categoryDto1, categoryDto2) -> (categoryDto1.getSort() == null ? 0 : categoryDto1.getSort()) - (categoryDto2.getSort() == null ? 0 : categoryDto2.getSort()))
                .collect(Collectors.toList());
        return collect;
    }

    //方案二
    private List<PmsCategoryDto> getPmsCategoryDtoByParentId(List<PmsCategoryDto> pmsCategoryDtoList, Long parentId){
             return pmsCategoryDtoList
                .stream()
                .filter(categoryDto -> categoryDto.getParentCid() == parentId)
                .map(pmsCategoryDto->{
                    if (pmsCategoryDto.getCatLevel()<= Constant.MAX_PMS_CATEGORY_LEVEL)
                        pmsCategoryDto.setChildCats(getPmsCategoryDtoByParentId(pmsCategoryDtoList,pmsCategoryDto.getCatId()));
                    return pmsCategoryDto;
                })
                .sorted((categoryDto1,categoryDto2)-> (categoryDto1.getSort()==null?0:categoryDto1.getSort()) - (categoryDto2.getSort()==null?0:categoryDto2.getSort()))
                .collect(Collectors.toList());
    }
}


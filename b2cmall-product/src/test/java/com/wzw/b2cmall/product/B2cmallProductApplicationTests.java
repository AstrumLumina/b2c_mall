package com.wzw.b2cmall.product;

import com.wzw.b2cmall.product.dao.PmsBrandDao;
import com.wzw.b2cmall.product.dao.PmsCategoryDao;
import com.wzw.b2cmall.product.pojo.entity.PmsBrandEntity;
import com.wzw.b2cmall.product.pojo.vo.PmsCategoryVo;
import com.wzw.b2cmall.product.service.PmsBrandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class B2cmallProductApplicationTests implements ApplicationContextAware {

    @Autowired
    private PmsBrandService pmsBrandService;

    @Autowired
    private PmsBrandDao pmsBrandDao;


    @Test
    void contextLoads() {
        List<PmsBrandEntity> pmsBrandEntityList= new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            pmsBrandEntityList.add(
                    new PmsBrandEntity()
                            .setDescript("test"+i)
                            .setName(i+"name")
                            .setLogo("testUrl")
                            .setShowStatus(i%1)
                            .setSort(i%4)
                            .setFirstLetter(i%10+"")
            );
        }
        pmsBrandDao.insertAll(pmsBrandEntityList);

        pmsBrandDao.selectAll().forEach(System.out::println);

        pmsBrandService.count();
        pmsBrandService.getById(2);
    }

    @Autowired
    private PmsCategoryDao pmsCategoryDao;

    @Test
    void testSelectPmaCategoryTree(){

        List<PmsCategoryVo> pmsCategoryVos = pmsCategoryDao.selectCatTree(0L);
        System.out.println(pmsCategoryVos);
    }

    @Autowired
    private DocumentationCache documentationCache;

    @Autowired
    private SwaggerResourcesProvider swaggerResourcesProvider;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Test
    void swaggerTest(){
//        Map<String, Documentation> all = documentationCache.all();
//        List<SwaggerResource> swaggerResources = swaggerResourcesProvider.get();
//
//        Map<String, Documentation> all1 = documentationCache.all();

        String[] beanNamesForType = applicationContext.getBeanNamesForType(SwaggerResourcesProvider.class);
        ObjectProvider<SwaggerResourcesProvider> beanProvider = applicationContext.getBeanProvider(SwaggerResourcesProvider.class);
    }


    @Autowired
    private ApplicationContext applicationContext2;

    @Test
    public void testAnnotationAutowireApplicationContext(){
        Assertions.assertNotNull(applicationContext2);
    }

}

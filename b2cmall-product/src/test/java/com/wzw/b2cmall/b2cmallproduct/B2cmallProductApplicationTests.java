package com.wzw.b2cmall.b2cmallproduct;

import com.wzw.b2cmall.b2cmallproduct.dao.PmsBrandDao;
import com.wzw.b2cmall.b2cmallproduct.entity.PmsBrandEntity;
import com.wzw.b2cmall.b2cmallproduct.service.PmsBrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class B2cmallProductApplicationTests {

    @Autowired
    PmsBrandService pmsBrandService;

    @Autowired
    PmsBrandDao pmsBrandDao;

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

}

package com.wzw.b2cmall.resource.feign;


import com.wzw.b2cmall.common.utils.R;
import com.wzw.b2cmall.product.pojo.dto.PmsCategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "b2cmall-product",path = "/b2cmall/product/pmscategory")
public interface PmsCategoryService {
    @GetMapping("/info/{catId}")
    R<PmsCategoryDto> getInfo(@PathVariable("catId") Long catId);

    @PostMapping("/save")
    R savePmscat(@RequestBody PmsCategoryDto pmsCategoryDto);

    @PostMapping("/update")
    R updatePmscat(@RequestBody PmsCategoryDto pmsCategoryDto);
}



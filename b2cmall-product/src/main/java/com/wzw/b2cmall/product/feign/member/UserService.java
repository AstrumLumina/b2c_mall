package com.wzw.b2cmall.product.feign.member;

import com.wzw.b2cmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("b2cmall-member")
public interface UserService {
    @GetMapping("/b2cmallmember/umsmember/info/{id}")
    R getInfo(@PathVariable("id") Long id);
}

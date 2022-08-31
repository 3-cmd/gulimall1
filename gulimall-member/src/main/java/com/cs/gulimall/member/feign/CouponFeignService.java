package com.cs.gulimall.member.feign;

import com.cs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 声明式远程调用
 */
@FeignClient("coupon")//要访问的服务的名称
public interface CouponFeignService {
    @RequestMapping("/coupon/coupon/member/list")
    public R memberCoupons();
}

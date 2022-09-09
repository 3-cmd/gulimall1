package com.cs.gulimall.product.exception;

import com.cs.common.utils.R;
import com.cs.exception.BizCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice(basePackages = "com.cs.gulimall.product")
//@ResponseBody
@Slf4j
@RestControllerAdvice(basePackages = "com.cs.gulimall.product")
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e){

        log.error("数据校验出现问题{},一场类型{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String,Object> map=new HashMap<>();
        bindingResult.getFieldErrors().forEach(item->{
            String field = item.getField();
            String message = item.getDefaultMessage();
            map.put(field,message);
        });
        return R.error(BizCodeEnum.VAILD_EXCEPTION.getCode(), BizCodeEnum.VAILD_EXCEPTION.getMessage()).put("data",map);
    }

    @ExceptionHandler(value =  Throwable.class)
    public R handleException(){
        return R.error(BizCodeEnum.UNKONW_EXCEPTION.getCode(), BizCodeEnum.UNKONW_EXCEPTION.getMessage());
    }

}

package com.cs.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ListValueConstrainValidator implements ConstraintValidator<ListValue,Integer> {
    /**
     * 初始化方法
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    //hashset的特点 无序不重复,无索引
    private Set<Integer> set=new HashSet<>();
    @Override
    public void initialize(ListValue constraintAnnotation) {
        //这里将我们写在注解后面的value数组的值取出来,遍历存放到set集合中
        int[] value = constraintAnnotation.value();
        Arrays.stream(value).forEach(item->{
            set.add(item);
        });
    }

    /**
     *
     * @param value 需要校验的值
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        //这里的value是我们前端传递过来的值,将这个值与我们set存储的值进行判断
        //如果set集合中存在这个前段传过来的值,证明前端传值正确否则错误
        return set.contains(value);
    }
}

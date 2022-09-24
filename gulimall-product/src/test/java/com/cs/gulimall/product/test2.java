package com.cs.gulimall.product;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class test2 {
    @Test
    public void test(){
        String key="key";
        Map<String,Object> map=new HashMap<>();
        map.put("key","cs");
        String cs = (String) map.get("cs");
        if (cs!=null){

        if (!cs.isBlank()){
            System.out.println(map.get("cs"));
        }
    }
    }

}

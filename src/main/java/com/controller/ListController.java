package com.controller;

import com.service.CommonService;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/list")
public class ListController {
    @Autowired
    private CommonService commonService;


    @ResponseBody
    @RequestMapping(value = "/getData1")
    public R getData1(){
        List<Map<String,Object>> list = commonService.getData1();
        List key = new ArrayList();
        List val = new ArrayList();
        Map returnMap = new HashMap<String, List>();
        for(Map<String,Object> m:list) {
            key.add(m.get("name"));
            val.add(m.get("value"));
        }
        returnMap.put("key", key);
        returnMap.put("val", val);
        return R.ok(returnMap);
    }

    @ResponseBody
    @RequestMapping(value = "/getData2")
    public R getData2(){
        List<Map<String,Object>> list = commonService.getData2();
        List key = new ArrayList();
        List val = new ArrayList();
        Map returnMap = new HashMap<String, List>();
        for(Map<String,Object> m:list) {
            key.add(m.get("name"));
            val.add(m.get("value"));
        }
        returnMap.put("key", key);
        returnMap.put("val", val);
        return R.ok(returnMap);
    }


}

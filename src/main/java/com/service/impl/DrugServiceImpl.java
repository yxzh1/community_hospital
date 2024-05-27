package com.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import com.dao.DrugDao;
import com.entity.DrugEntity;
import com.service.DrugService;
import com.entity.view.DrugView;

/**
 * 药品 服务实现类
 */
@Service("yaopinService")
@Transactional
public class DrugServiceImpl extends ServiceImpl<DrugDao, DrugEntity> implements DrugService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        Page<DrugView> page =new Query<DrugView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}

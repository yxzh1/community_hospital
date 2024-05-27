package com.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import com.dao.PatientDao;
import com.entity.PatientEntity;
import com.service.PatientService;
import com.entity.view.PatientView;

/**
 * 病人 服务实现类
 */
@Service("bingrenService")
@Transactional
public class PatientServiceImpl extends ServiceImpl<PatientDao, PatientEntity> implements PatientService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        Page<PatientView> page =new Query<PatientView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}

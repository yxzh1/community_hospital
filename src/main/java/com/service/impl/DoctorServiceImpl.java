package com.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import com.dao.DoctorDao;
import com.entity.DoctorEntity;
import com.service.DoctorService;
import com.entity.view.DoctorView;

/**
 * 医生 服务实现类
 */
@Service("yishengService")
@Transactional
public class DoctorServiceImpl extends ServiceImpl<DoctorDao, DoctorEntity> implements DoctorService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        Page<DoctorView> page =new Query<DoctorView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}

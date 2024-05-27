package com.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import com.dao.DoctorRegistrationDao;
import com.entity.DoctorRegistrationEntity;
import com.service.DoctorRegistrationService;
import com.entity.view.DoctorRegistrationView;

/**
 * 医生挂号 服务实现类
 */
@Service("yishengGuahaoService")
@Transactional
public class DoctorRegistrationServiceImpl extends ServiceImpl<DoctorRegistrationDao, DoctorRegistrationEntity> implements DoctorRegistrationService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        Page<DoctorRegistrationView> page =new Query<DoctorRegistrationView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}

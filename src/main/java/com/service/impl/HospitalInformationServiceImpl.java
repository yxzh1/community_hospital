package com.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import com.dao.HospitalInformationDao;
import com.entity.HospitalInformationEntity;
import com.service.HospitalInformationService;
import com.entity.view.HospitalInformationView;

/**
 * 医院资讯 服务实现类
 */
@Service("gonggaoService")
@Transactional
public class HospitalInformationServiceImpl extends ServiceImpl<HospitalInformationDao, HospitalInformationEntity> implements HospitalInformationService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        Page<HospitalInformationView> page =new Query<HospitalInformationView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}

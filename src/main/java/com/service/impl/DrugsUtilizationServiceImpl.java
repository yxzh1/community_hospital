package com.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import com.dao.DrugsUtilizationDao;
import com.entity.DrugsUtilizationEntity;
import com.service.DrugsUtilizationService;
import com.entity.view.DrugsUtilizationView;

/**
 * 药品使用 服务实现类
 */
@Service("yaopinshiyongService")
@Transactional
public class DrugsUtilizationServiceImpl extends ServiceImpl<DrugsUtilizationDao, DrugsUtilizationEntity> implements DrugsUtilizationService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        Page<DrugsUtilizationView> page =new Query<DrugsUtilizationView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}

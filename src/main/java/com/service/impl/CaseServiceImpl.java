package com.service.impl;

import com.entity.CaseEntity;
import com.entity.view.CaseView;
import org.springframework.stereotype.Service;

import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import com.dao.CaseDao;
import com.service.CaseService;

/**
 * 病例 服务实现类
 */
@Service("bingliService")
@Transactional
public class CaseServiceImpl extends ServiceImpl<CaseDao, CaseEntity> implements CaseService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        Page<CaseView> page =new Query<CaseView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}

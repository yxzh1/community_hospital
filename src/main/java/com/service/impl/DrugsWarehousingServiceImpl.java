package com.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import com.dao.DrugsWarehousingDao;
import com.entity.DrugsWarehousingEntity;
import com.service.DrugsWarehousingService;
import com.entity.view.DrugsWarehousingView;

/**
 * 药品入库 服务实现类
 */
@Service("yaopinrukuService")
@Transactional
public class DrugsWarehousingServiceImpl extends ServiceImpl<DrugsWarehousingDao, DrugsWarehousingEntity> implements DrugsWarehousingService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        Page<DrugsWarehousingView> page =new Query<DrugsWarehousingView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}

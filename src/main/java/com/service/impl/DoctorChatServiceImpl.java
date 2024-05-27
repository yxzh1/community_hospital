package com.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import com.dao.DoctorChatDao;
import com.entity.DoctorChatEntity;
import com.service.DoctorChatService;
import com.entity.view.DoctorChatView;

/**
 * 医生咨询 服务实现类
 */
@Service("yishengChatService")
@Transactional
public class DoctorChatServiceImpl extends ServiceImpl<DoctorChatDao, DoctorChatEntity> implements DoctorChatService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        Page<DoctorChatView> page =new Query<DoctorChatView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}

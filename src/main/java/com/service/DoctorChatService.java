package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DoctorChatEntity;
import java.util.Map;

/**
 * 医生咨询 服务类
 */
public interface DoctorChatService extends IService<DoctorChatEntity> {

    /**
    * @param params 查询参数
    * @return 带分页的查询出来的数据
    */
     PageUtils queryPage(Map<String, Object> params);

}
package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DoctorRegistrationEntity;
import java.util.Map;

/**
 * 医生挂号 服务类
 */
public interface DoctorRegistrationService extends IService<DoctorRegistrationEntity> {

    /**
    * @param params 查询参数
    * @return 带分页的查询出来的数据
    */
     PageUtils queryPage(Map<String, Object> params);

}
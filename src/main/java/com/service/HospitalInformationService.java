package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.HospitalInformationEntity;
import java.util.Map;

/**
 * 医院资讯 服务类
 */
public interface HospitalInformationService extends IService<HospitalInformationEntity> {

    /**
    * @param params 查询参数
    * @return 带分页的查询出来的数据
    */
     PageUtils queryPage(Map<String, Object> params);

}
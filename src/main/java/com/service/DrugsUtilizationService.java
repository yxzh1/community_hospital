package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DrugsUtilizationEntity;
import java.util.Map;

/**
 * 药品使用 服务类
 */
public interface DrugsUtilizationService extends IService<DrugsUtilizationEntity> {

    /**
    * @param params 查询参数
    * @return 带分页的查询出来的数据
    */
     PageUtils queryPage(Map<String, Object> params);

}
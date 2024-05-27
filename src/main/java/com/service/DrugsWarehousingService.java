package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DrugsWarehousingEntity;
import java.util.Map;

/**
 * 药品入库 服务类
 */
public interface DrugsWarehousingService extends IService<DrugsWarehousingEntity> {

    /**
    * @param params 查询参数
    * @return 带分页的查询出来的数据
    */
     PageUtils queryPage(Map<String, Object> params);

}
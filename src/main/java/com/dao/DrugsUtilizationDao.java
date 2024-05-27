package com.dao;

import com.entity.DrugsUtilizationEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.DrugsUtilizationView;

/**
 * 药品使用 Dao 接口
 *
 * @author 
 */
public interface DrugsUtilizationDao extends BaseMapper<DrugsUtilizationEntity> {

   List<DrugsUtilizationView> selectListView(Pagination page, @Param("params")Map<String,Object> params);

}

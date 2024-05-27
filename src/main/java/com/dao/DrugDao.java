package com.dao;

import com.entity.DrugEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.DrugView;

/**
 * 药品 Dao 接口
 *
 * @author 
 */
public interface DrugDao extends BaseMapper<DrugEntity> {

   List<DrugView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}

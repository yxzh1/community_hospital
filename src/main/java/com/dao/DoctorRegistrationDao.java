package com.dao;

import com.entity.DoctorRegistrationEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.DoctorRegistrationView;

/**
 * 医生挂号 Dao 接口
 *
 * @author 
 */
public interface DoctorRegistrationDao extends BaseMapper<DoctorRegistrationEntity> {

   List<DoctorRegistrationView> selectListView(Pagination page, @Param("params")Map<String,Object> params);

}

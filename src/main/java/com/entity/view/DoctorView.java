package com.entity.view;

import com.annotation.ColumnInfo;
import com.entity.DoctorEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.io.Serializable;

/**
* 医生
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("yisheng")
public class DoctorView extends DoctorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	* 性别的值
	*/
	@ColumnInfo(comment="性别的字典表值",type="varchar(200)")
	private String sexValue;
	/**
	* 科室的值
	*/
	@ColumnInfo(comment="科室的字典表值",type="varchar(200)")
	private String keshiValue;
	/**
	* 职位的值
	*/
	@ColumnInfo(comment="职位的字典表值",type="varchar(200)")
	private String zhiweiValue;




	public DoctorView() {

	}

	public DoctorView(DoctorEntity yishengEntity) {
		try {
			BeanUtils.copyProperties(this, yishengEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//当前表的
	/**
	* 获取： 性别的值
	*/
	public String getSexValue() {
		return sexValue;
	}
	/**
	* 设置： 性别的值
	*/
	public void setSexValue(String sexValue) {
		this.sexValue = sexValue;
	}
	//当前表的
	/**
	* 获取： 科室的值
	*/
	public String getKeshiValue() {
		return keshiValue;
	}
	/**
	* 设置： 科室的值
	*/
	public void setKeshiValue(String keshiValue) {
		this.keshiValue = keshiValue;
	}
	//当前表的
	/**
	* 获取： 职位的值
	*/
	public String getZhiweiValue() {
		return zhiweiValue;
	}
	/**
	* 设置： 职位的值
	*/
	public void setZhiweiValue(String zhiweiValue) {
		this.zhiweiValue = zhiweiValue;
	}




	@Override
	public String toString() {
		return "DoctorView{" +
			", sexValue=" + sexValue +
			", keshiValue=" + keshiValue +
			", zhiweiValue=" + zhiweiValue +
			"} " + super.toString();
	}
}

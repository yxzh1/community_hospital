package com.entity.view;

import com.annotation.ColumnInfo;
import com.entity.DrugsUtilizationEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.io.Serializable;

/**
* 药品使用
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("yaopinshiyong")
public class DrugsUtilizationView extends DrugsUtilizationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表

	//级联表 病人
					 
		/**
		* 病人 的 用户
		*/
		@ColumnInfo(comment="用户",type="int(11)")
		private Integer bingrenYonghuId;
		/**
		* 看护人姓名
		*/

		@ColumnInfo(comment="看护人姓名",type="varchar(200)")
		private String kanhurenName;
		/**
		* 看护人联系方式
		*/

		@ColumnInfo(comment="看护人联系方式",type="varchar(200)")
		private String kanhurenPhone;
		/**
		* 病人姓名
		*/

		@ColumnInfo(comment="病人姓名",type="varchar(200)")
		private String bingrenName;
		/**
		* 病人手机号
		*/

		@ColumnInfo(comment="病人手机号",type="varchar(200)")
		private String bingrenPhone;
		/**
		* 病人身份证号
		*/

		@ColumnInfo(comment="病人身份证号",type="varchar(200)")
		private String bingrenIdNumber;
		/**
		* 病人类型
		*/
		@ColumnInfo(comment="病人类型",type="int(11)")
		private Integer bingrenTypes;
			/**
			* 病人类型的值
			*/
			@ColumnInfo(comment="病人类型的字典表值",type="varchar(200)")
			private String bingrenValue;
		/**
		* 年龄
		*/

		@ColumnInfo(comment="年龄",type="int(11)")
		private Integer age;
		/**
		* 病人照片
		*/

		@ColumnInfo(comment="病人照片",type="varchar(200)")
		private String bingrenPhoto;
		/**
		* 病人描述
		*/

		@ColumnInfo(comment="病人描述",type="text")
		private String bingrenContent;
	//级联表 药品
		/**
		* 药品名称
		*/

		@ColumnInfo(comment="药品名称",type="varchar(200)")
		private String yaopinName;
		/**
		* 药品编号
		*/

		@ColumnInfo(comment="药品编号",type="varchar(200)")
		private String yaopinUuidNumber;
		/**
		* 药品照片
		*/

		@ColumnInfo(comment="药品照片",type="varchar(200)")
		private String yaopinPhoto;
		/**
		* 生产厂家
		*/

		@ColumnInfo(comment="生产厂家",type="varchar(200)")
		private String shengchanchangjia;
		/**
		* 药品类型
		*/
		@ColumnInfo(comment="药品类型",type="int(11)")
		private Integer yaopinTypes;
			/**
			* 药品类型的值
			*/
			@ColumnInfo(comment="药品类型的字典表值",type="varchar(200)")
			private String yaopinValue;
		/**
		* 药品库存
		*/

		@ColumnInfo(comment="药品库存",type="int(11)")
		private Integer yaopinKucunNumber;
		/**
		* 药品进价
		*/
		@ColumnInfo(comment="药品进价",type="decimal(10,2)")
		private Double yaopinJinjiaMoney;
		/**
		* 售价
		*/
		@ColumnInfo(comment="售价",type="decimal(10,2)")
		private Double yaopinNewMoney;
		/**
		* 药品功效
		*/

		@ColumnInfo(comment="药品功效",type="text")
		private String yaopinGongxiaoContent;
		/**
		* 药品禁忌
		*/

		@ColumnInfo(comment="药品禁忌",type="text")
		private String yaopinJinjiContent;
		/**
		* 注意事项
		*/

		@ColumnInfo(comment="注意事项",type="text")
		private String yaopinZhuyiContent;
		/**
		* 药品详情
		*/

		@ColumnInfo(comment="药品详情",type="text")
		private String yaopinContent;
	//级联表 医生
		/**
		* 医生姓名
		*/

		@ColumnInfo(comment="医生姓名",type="varchar(200)")
		private String yishengName;
		/**
		* 医生手机号
		*/

		@ColumnInfo(comment="医生手机号",type="varchar(200)")
		private String yishengPhone;
		/**
		* 医生身份证号
		*/

		@ColumnInfo(comment="医生身份证号",type="varchar(200)")
		private String yishengIdNumber;
		/**
		* 医生头像
		*/

		@ColumnInfo(comment="医生头像",type="varchar(200)")
		private String yishengPhoto;
		/**
		* 坐诊时间
		*/

		@ColumnInfo(comment="坐诊时间",type="varchar(200)")
		private String zuozhenShijian;
		/**
		* 挂号金额
		*/
		@ColumnInfo(comment="挂号金额",type="decimal(10,2)")
		private Double guahaojine;
		/**
		* 每天挂号数量
		*/

		@ColumnInfo(comment="每天挂号数量",type="int(11)")
		private Integer guahaoshuliang;
		/**
		* 科室
		*/
		@ColumnInfo(comment="科室",type="int(11)")
		private Integer keshiTypes;
			/**
			* 科室的值
			*/
			@ColumnInfo(comment="科室的字典表值",type="varchar(200)")
			private String keshiValue;
		/**
		* 职位
		*/
		@ColumnInfo(comment="职位",type="int(11)")
		private Integer zhiweiTypes;
			/**
			* 职位的值
			*/
			@ColumnInfo(comment="职位的字典表值",type="varchar(200)")
			private String zhiweiValue;
		/**
		* 医生邮箱
		*/

		@ColumnInfo(comment="医生邮箱",type="varchar(200)")
		private String yishengEmail;
		/**
		* 医生介绍
		*/

		@ColumnInfo(comment="医生介绍",type="text")
		private String yishengContent;

	//重复字段
			/**
			* 重复字段 的types
			*/
			@ColumnInfo(comment="重复字段 的types",type="Integer")
			private Integer sexTypes;
				@ColumnInfo(comment="重复字段 的值",type="varchar(200)")
				private String sexValue;


	public DrugsUtilizationView() {

	}

	public DrugsUtilizationView(DrugsUtilizationEntity yaopinshiyongEntity) {
		try {
			BeanUtils.copyProperties(this, yaopinshiyongEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}





	//级联表的get和set 病人
		/**
		* 获取：病人 的 用户
		*/
		public Integer getBingrenYonghuId() {
			return bingrenYonghuId;
		}
		/**
		* 设置：病人 的 用户
		*/
		public void setBingrenYonghuId(Integer bingrenYonghuId) {
			this.bingrenYonghuId = bingrenYonghuId;
		}

		/**
		* 获取： 看护人姓名
		*/
		public String getKanhurenName() {
			return kanhurenName;
		}
		/**
		* 设置： 看护人姓名
		*/
		public void setKanhurenName(String kanhurenName) {
			this.kanhurenName = kanhurenName;
		}

		/**
		* 获取： 看护人联系方式
		*/
		public String getKanhurenPhone() {
			return kanhurenPhone;
		}
		/**
		* 设置： 看护人联系方式
		*/
		public void setKanhurenPhone(String kanhurenPhone) {
			this.kanhurenPhone = kanhurenPhone;
		}

		/**
		* 获取： 病人姓名
		*/
		public String getBingrenName() {
			return bingrenName;
		}
		/**
		* 设置： 病人姓名
		*/
		public void setBingrenName(String bingrenName) {
			this.bingrenName = bingrenName;
		}

		/**
		* 获取： 病人手机号
		*/
		public String getBingrenPhone() {
			return bingrenPhone;
		}
		/**
		* 设置： 病人手机号
		*/
		public void setBingrenPhone(String bingrenPhone) {
			this.bingrenPhone = bingrenPhone;
		}

		/**
		* 获取： 病人身份证号
		*/
		public String getBingrenIdNumber() {
			return bingrenIdNumber;
		}
		/**
		* 设置： 病人身份证号
		*/
		public void setBingrenIdNumber(String bingrenIdNumber) {
			this.bingrenIdNumber = bingrenIdNumber;
		}
		/**
		* 获取： 病人类型
		*/
		public Integer getBingrenTypes() {
			return bingrenTypes;
		}
		/**
		* 设置： 病人类型
		*/
		public void setBingrenTypes(Integer bingrenTypes) {
			this.bingrenTypes = bingrenTypes;
		}


			/**
			* 获取： 病人类型的值
			*/
			public String getBingrenValue() {
				return bingrenValue;
			}
			/**
			* 设置： 病人类型的值
			*/
			public void setBingrenValue(String bingrenValue) {
				this.bingrenValue = bingrenValue;
			}

		/**
		* 获取： 年龄
		*/
		public Integer getAge() {
			return age;
		}
		/**
		* 设置： 年龄
		*/
		public void setAge(Integer age) {
			this.age = age;
		}

		/**
		* 获取： 病人照片
		*/
		public String getBingrenPhoto() {
			return bingrenPhoto;
		}
		/**
		* 设置： 病人照片
		*/
		public void setBingrenPhoto(String bingrenPhoto) {
			this.bingrenPhoto = bingrenPhoto;
		}

		/**
		* 获取： 病人描述
		*/
		public String getBingrenContent() {
			return bingrenContent;
		}
		/**
		* 设置： 病人描述
		*/
		public void setBingrenContent(String bingrenContent) {
			this.bingrenContent = bingrenContent;
		}
	//级联表的get和set 药品

		/**
		* 获取： 药品名称
		*/
		public String getYaopinName() {
			return yaopinName;
		}
		/**
		* 设置： 药品名称
		*/
		public void setYaopinName(String yaopinName) {
			this.yaopinName = yaopinName;
		}

		/**
		* 获取： 药品编号
		*/
		public String getYaopinUuidNumber() {
			return yaopinUuidNumber;
		}
		/**
		* 设置： 药品编号
		*/
		public void setYaopinUuidNumber(String yaopinUuidNumber) {
			this.yaopinUuidNumber = yaopinUuidNumber;
		}

		/**
		* 获取： 药品照片
		*/
		public String getYaopinPhoto() {
			return yaopinPhoto;
		}
		/**
		* 设置： 药品照片
		*/
		public void setYaopinPhoto(String yaopinPhoto) {
			this.yaopinPhoto = yaopinPhoto;
		}

		/**
		* 获取： 生产厂家
		*/
		public String getShengchanchangjia() {
			return shengchanchangjia;
		}
		/**
		* 设置： 生产厂家
		*/
		public void setShengchanchangjia(String shengchanchangjia) {
			this.shengchanchangjia = shengchanchangjia;
		}
		/**
		* 获取： 药品类型
		*/
		public Integer getYaopinTypes() {
			return yaopinTypes;
		}
		/**
		* 设置： 药品类型
		*/
		public void setYaopinTypes(Integer yaopinTypes) {
			this.yaopinTypes = yaopinTypes;
		}


			/**
			* 获取： 药品类型的值
			*/
			public String getYaopinValue() {
				return yaopinValue;
			}
			/**
			* 设置： 药品类型的值
			*/
			public void setYaopinValue(String yaopinValue) {
				this.yaopinValue = yaopinValue;
			}

		/**
		* 获取： 药品库存
		*/
		public Integer getYaopinKucunNumber() {
			return yaopinKucunNumber;
		}
		/**
		* 设置： 药品库存
		*/
		public void setYaopinKucunNumber(Integer yaopinKucunNumber) {
			this.yaopinKucunNumber = yaopinKucunNumber;
		}

		/**
		* 获取： 药品进价
		*/
		public Double getYaopinJinjiaMoney() {
			return yaopinJinjiaMoney;
		}
		/**
		* 设置： 药品进价
		*/
		public void setYaopinJinjiaMoney(Double yaopinJinjiaMoney) {
			this.yaopinJinjiaMoney = yaopinJinjiaMoney;
		}

		/**
		* 获取： 售价
		*/
		public Double getYaopinNewMoney() {
			return yaopinNewMoney;
		}
		/**
		* 设置： 售价
		*/
		public void setYaopinNewMoney(Double yaopinNewMoney) {
			this.yaopinNewMoney = yaopinNewMoney;
		}

		/**
		* 获取： 药品功效
		*/
		public String getYaopinGongxiaoContent() {
			return yaopinGongxiaoContent;
		}
		/**
		* 设置： 药品功效
		*/
		public void setYaopinGongxiaoContent(String yaopinGongxiaoContent) {
			this.yaopinGongxiaoContent = yaopinGongxiaoContent;
		}

		/**
		* 获取： 药品禁忌
		*/
		public String getYaopinJinjiContent() {
			return yaopinJinjiContent;
		}
		/**
		* 设置： 药品禁忌
		*/
		public void setYaopinJinjiContent(String yaopinJinjiContent) {
			this.yaopinJinjiContent = yaopinJinjiContent;
		}

		/**
		* 获取： 注意事项
		*/
		public String getYaopinZhuyiContent() {
			return yaopinZhuyiContent;
		}
		/**
		* 设置： 注意事项
		*/
		public void setYaopinZhuyiContent(String yaopinZhuyiContent) {
			this.yaopinZhuyiContent = yaopinZhuyiContent;
		}

		/**
		* 获取： 药品详情
		*/
		public String getYaopinContent() {
			return yaopinContent;
		}
		/**
		* 设置： 药品详情
		*/
		public void setYaopinContent(String yaopinContent) {
			this.yaopinContent = yaopinContent;
		}
	//级联表的get和set 医生

		/**
		* 获取： 医生姓名
		*/
		public String getYishengName() {
			return yishengName;
		}
		/**
		* 设置： 医生姓名
		*/
		public void setYishengName(String yishengName) {
			this.yishengName = yishengName;
		}

		/**
		* 获取： 医生手机号
		*/
		public String getYishengPhone() {
			return yishengPhone;
		}
		/**
		* 设置： 医生手机号
		*/
		public void setYishengPhone(String yishengPhone) {
			this.yishengPhone = yishengPhone;
		}

		/**
		* 获取： 医生身份证号
		*/
		public String getYishengIdNumber() {
			return yishengIdNumber;
		}
		/**
		* 设置： 医生身份证号
		*/
		public void setYishengIdNumber(String yishengIdNumber) {
			this.yishengIdNumber = yishengIdNumber;
		}

		/**
		* 获取： 医生头像
		*/
		public String getYishengPhoto() {
			return yishengPhoto;
		}
		/**
		* 设置： 医生头像
		*/
		public void setYishengPhoto(String yishengPhoto) {
			this.yishengPhoto = yishengPhoto;
		}

		/**
		* 获取： 坐诊时间
		*/
		public String getZuozhenShijian() {
			return zuozhenShijian;
		}
		/**
		* 设置： 坐诊时间
		*/
		public void setZuozhenShijian(String zuozhenShijian) {
			this.zuozhenShijian = zuozhenShijian;
		}

		/**
		* 获取： 挂号金额
		*/
		public Double getGuahaojine() {
			return guahaojine;
		}
		/**
		* 设置： 挂号金额
		*/
		public void setGuahaojine(Double guahaojine) {
			this.guahaojine = guahaojine;
		}

		/**
		* 获取： 每天挂号数量
		*/
		public Integer getGuahaoshuliang() {
			return guahaoshuliang;
		}
		/**
		* 设置： 每天挂号数量
		*/
		public void setGuahaoshuliang(Integer guahaoshuliang) {
			this.guahaoshuliang = guahaoshuliang;
		}
		/**
		* 获取： 科室
		*/
		public Integer getKeshiTypes() {
			return keshiTypes;
		}
		/**
		* 设置： 科室
		*/
		public void setKeshiTypes(Integer keshiTypes) {
			this.keshiTypes = keshiTypes;
		}


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
		/**
		* 获取： 职位
		*/
		public Integer getZhiweiTypes() {
			return zhiweiTypes;
		}
		/**
		* 设置： 职位
		*/
		public void setZhiweiTypes(Integer zhiweiTypes) {
			this.zhiweiTypes = zhiweiTypes;
		}


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

		/**
		* 获取： 医生邮箱
		*/
		public String getYishengEmail() {
			return yishengEmail;
		}
		/**
		* 设置： 医生邮箱
		*/
		public void setYishengEmail(String yishengEmail) {
			this.yishengEmail = yishengEmail;
		}

		/**
		* 获取： 医生介绍
		*/
		public String getYishengContent() {
			return yishengContent;
		}
		/**
		* 设置： 医生介绍
		*/
		public void setYishengContent(String yishengContent) {
			this.yishengContent = yishengContent;
		}

	//重复字段
			/**
			* 获取： 重复字段 的types
			*/
			public Integer getSexTypes() {
			return sexTypes;
			}
			/**
			* 设置： 重复字段 的types
			*/
			public void setSexTypes(Integer sexTypes) {
			this.sexTypes = sexTypes;
			}
				public String getSexValue() {
				return sexValue;
				}
				public void setSexValue(String sexValue) {
				this.sexValue = sexValue;
				}

	@Override
	public String toString() {
		return "YaopinshiyongView{" +
			", kanhurenName=" + kanhurenName +
			", kanhurenPhone=" + kanhurenPhone +
			", bingrenName=" + bingrenName +
			", bingrenPhone=" + bingrenPhone +
			", bingrenIdNumber=" + bingrenIdNumber +
			", age=" + age +
			", bingrenPhoto=" + bingrenPhoto +
			", bingrenContent=" + bingrenContent +
			", yaopinName=" + yaopinName +
			", yaopinUuidNumber=" + yaopinUuidNumber +
			", yaopinPhoto=" + yaopinPhoto +
			", shengchanchangjia=" + shengchanchangjia +
			", yaopinKucunNumber=" + yaopinKucunNumber +
			", yaopinJinjiaMoney=" + yaopinJinjiaMoney +
			", yaopinNewMoney=" + yaopinNewMoney +
			", yaopinGongxiaoContent=" + yaopinGongxiaoContent +
			", yaopinJinjiContent=" + yaopinJinjiContent +
			", yaopinZhuyiContent=" + yaopinZhuyiContent +
			", yaopinContent=" + yaopinContent +
			", yishengName=" + yishengName +
			", yishengPhone=" + yishengPhone +
			", yishengIdNumber=" + yishengIdNumber +
			", yishengPhoto=" + yishengPhoto +
			", zuozhenShijian=" + zuozhenShijian +
			", guahaojine=" + guahaojine +
			", guahaoshuliang=" + guahaoshuliang +
			", yishengEmail=" + yishengEmail +
			", yishengContent=" + yishengContent +
			"} " + super.toString();
	}
}

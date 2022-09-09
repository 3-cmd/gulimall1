package com.cs.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.cs.valid.AddGroup;
import com.cs.valid.ListValue;
import com.cs.valid.UpdateGroup;
import com.cs.valid.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;


import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author cs
 * @email 1397368928@qq.com
 * @date 2022-08-29 09:44:54
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	@Null(message = "新增不能指定id",groups = {AddGroup.class})
	@NotNull(message = "修改必须提供id",groups = {UpdateGroup.class, UpdateStatusGroup.class})
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "名称不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotBlank(message = "logo地址不能为空",groups = {AddGroup.class})
	@URL(message = "logo必须是一个合法url地址",groups = {AddGroup.class,UpdateGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	//@NotBlank(message = "介绍不能为空",groups = {AddGroup.class})
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ListValue(value = {0,1},groups = {AddGroup.class,UpdateGroup.class,UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotBlank(message = "首字母不能为空",groups = {AddGroup.class})
	@Pattern(regexp = "^[a-zA-Z]$",message = "检索首字母必须是一个字母",groups = {AddGroup.class,UpdateGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(message = "排序不能为空",groups = {AddGroup.class})
	@Min(value = 0,message = "排序b=必须大于等于0",groups = {AddGroup.class,UpdateGroup.class})
	private Integer sort;

}

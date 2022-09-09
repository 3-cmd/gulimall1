package com.cs.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
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
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "名称不能为空")
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotBlank(message = "logo地址不能为空")
	@URL(message = "logo必须是一个合法url地址")
	private String logo;
	/**
	 * 介绍
	 */
	@NotBlank(message = "介绍不能为空")
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotBlank(message = "首字母不能为空")
	@Pattern(regexp = "/^[a-zA-Z]$/",message = "检索首字母必须是一个字母")
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(message = "排序不能为空")
	@Min(value = 0,message = "排序b=必须大于等于0")
	private Integer sort;

}

package com.qzsoft.tah.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sz_dic2_b")
public class SzDic2B  implements Serializable {

	@Id
	@GeneratedValue(generator = "idOrGenerate")
	@GenericGenerator(name = "idOrGenerate", strategy = "com.qzsoft.tah.utils.IdOrGenerate")
	private Integer id;

	private String code;

	@Column(name = "parent_code")
	private String parentCode;

	private String val;

	@Column(name = "level_")
	private Integer level;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "update_time")
	private java.util.Date updateTime;

	@Column(name = "disp_or")
	private Integer dispOr;

	private String remark;

	@Column(name = "up_ver")
	private Integer upVer;

	@Column(name = "is_sys_dic")
	private String isSysDic;

	private String st;

	@Column(name = "dic_prefix")
	private String dicPrefix;

	private String val1;

	private String val2;

	@Column(name = "is_content")
	private String isContent;

}

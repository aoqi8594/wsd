package com.qzsoft.tah.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "wsd_export_l")
public class WsdExportL  implements Serializable {

	@Id
	@GeneratedValue(generator = "idOrGenerate")
	@GenericGenerator(name = "idOrGenerate", strategy = "com.qzsoft.tah.utils.IdOrGenerate")
	private Long id;

	private String st;

	@Column(name = "up_ver")
	private Integer upVer;

	@Column(name = "create_time")
	private LocalDateTime createTime;

	@Column(name = "update_time")
	private LocalDateTime updateTime;

	@Column(name = "creator_lname")
	private String creatorLname;

	@Column(name = "operator_lname")
	private String operatorLname;

	@Column(name = "report_name")
	private String reportName;

	@Column(name = "export_type")
	private String exportType;

	@Column(name = "file_path")
	private String filePath;

}

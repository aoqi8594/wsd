package com.qzsoft.tah.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "wsd_dev_export_f")
public class WsdDevExportF  implements Serializable {

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

	@Column(name = "export_id")
	private Long exportId;

	@Column(name = "dev_id")
	private Long devId;

}

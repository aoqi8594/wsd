package com.qzsoft.tah.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "wsd_dev_tah_h")
public class WsdDevTahH  implements Serializable {

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

	private String temperature;

	@Column(name = "temp_up")
	private String tempUp;

	@Column(name = "temp_low")
	private String tempLow;

	private String humidity;

	@Column(name = "hum_up")
	private String humUp;

	@Column(name = "hum_low")
	private String humLow;

	@Column(name = "dev_id")
	private Long devId;

	@Column(name = "dev_name")
	private String devName;

	@Column(name = "dev_no")
	private String devNo;

	@Column(name = "alarm_yn")
	private String alarmYn;

	@Column(name = "belong_type")
	private String belongType;

	@Column(name = "use_type")
	private String useType;

	private String location;

}

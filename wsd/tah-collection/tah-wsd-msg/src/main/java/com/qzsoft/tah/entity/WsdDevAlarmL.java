package com.qzsoft.tah.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "wsd_dev_alarm_l")
public class WsdDevAlarmL  implements Serializable {

	@Id
	@GeneratedValue(generator = "idOrGenerate")
	@GenericGenerator(name = "idOrGenerate", strategy = "com.qzsoft.tah.utils.IdOrGenerate")
	private Long id;

	private String st;

	@Column(name = "up_ver")
	private Integer upVer;

	@Column(name = "create_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	@Column(name = "update_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;

	@Column(name = "creator_lname")
	private String creatorLname;

	@Column(name = "operator_lname")
	private String operatorLname;

	@Column(name = "ignore_yn")
	private String ignoreYn;

	@Column(name = "dev_id")
	private Long devId;

	@Column(name = "dev_no")
	private String devNo;

	@Column(name = "dev_name")
	private String devName;

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

	@Column(name = "alarm_yn")
	private String alarmYn;

	@Column(name = "belong_type")
	private String belongType;

	private String location;

	@Column(name = "net_st")
	private String netSt;

	@Column(name = "duty_name")
	private String dutyName;

	@Column(name = "duty_cont_phon")
	private String dutyContPhon;

	@Column(name = "alarm_content")
	private String alarmContent;

	@Column(name = "trea_results")
	private String treaResults;

	@Column(name = "send_msg_yn")
	private String sendMsgYn;

}

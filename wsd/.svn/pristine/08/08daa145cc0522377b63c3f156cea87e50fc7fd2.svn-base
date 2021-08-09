package com.qzsoft.tah.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "wsd_alarm_wx_l")
public class WsdAlarmWxL  implements Serializable {

	@Id
	@GeneratedValue(generator = "idOrGenerate")
	@GenericGenerator(name = "idOrGenerate", strategy = "com.qzsoft.tah.utils.IdOrGenerate")
	private Long id;

	@Column(name = "dev_no")
	private String devNo;

	@Column(name = "send_no")
	private String sendNo;

	@Column(name = "sender_name")
	private String senderName;

	@Column(name = "sendee_no")
	private String sendeeNo;

	@Column(name = "sendee_name")
	private String sendeeName;

	private String title;

	private String content;

	@Column(name = "send_dm")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime sendDm;

	@Column(name = "business_type")
	private String businessType;

	@Column(name = "msg_type")
	private String msgType;

	@Column(name = "receive_type")
	private String receiveType;

	@Column(name = "isread_yn")
	private String isreadYn;

	@Column(name = "send_st")
	private String sendSt;

	@Column(name = "fail_reason")
	private String failReason;

}

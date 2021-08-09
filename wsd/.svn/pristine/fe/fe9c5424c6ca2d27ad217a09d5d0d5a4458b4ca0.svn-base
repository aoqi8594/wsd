package com.qzsoft.tah.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "wsd_car_b")
public class WsdCarB  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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

	@Column(name = "car_no")
	private String carNo;

	@Column(name = "contact_phone")
	private String contactPhone;

	@Column(name = "start_place")
	private String startPlace;

	@Column(name = "end_place")
	private String endPlace;

	@Column(name = "dev_id")
	private Long devId;

	@Column(name = "driver_name")
	private String driverName;

	private String tachograph;

	private String mark;

	@Column(name="driver_lname")
	private String driverLname;
}

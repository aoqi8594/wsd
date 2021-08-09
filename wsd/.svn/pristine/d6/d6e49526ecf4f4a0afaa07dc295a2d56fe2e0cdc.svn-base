package com.qzsoft.tah.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "wsd_car_gps_l")
public class WsdCarGpsL  implements Serializable {

	@Id
	@GeneratedValue(generator = "idOrGenerate")
	@GenericGenerator(name = "idOrGenerate", strategy = "com.qzsoft.tah.utils.IdOrGenerate")
	private Long id;

	@Column(name = "car_task_id")
	private Long carTaskId;

	@Column(name = "device_id")
	private String deviceId;

	private String latitude;

	private String longitude;

	@Column(name = "create_time")
	private LocalDateTime createTime;

	@Column(name = "sync_time")
	private LocalDateTime syncTime;

	private String lat;

	private String lng;

}

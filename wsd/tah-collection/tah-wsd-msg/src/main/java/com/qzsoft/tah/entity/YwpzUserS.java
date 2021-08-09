package com.qzsoft.tah.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ywpz_user_s")
public class YwpzUserS  implements Serializable {

	@Id
	@GeneratedValue(generator = "idOrGenerate")
	@GenericGenerator(name = "idOrGenerate", strategy = "com.qzsoft.tah.utils.IdOrGenerate")
	private Long id;

	private String prn;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_type")
	private String userType;

	@Column(name = "user_role")
	private String userRole;

	@Column(name = "login_name")
	private String loginName;

	private String email;

	@Column(name = "mobile_phone")
	private String mobilePhone;

	private String st;

	@Column(name = "create_time")
	private LocalDateTime createTime;

	@Column(name = "update_time")
	private LocalDateTime updateTime;

	@Column(name = "avatar_icon_uri")
	private String avatarIconUri;

	@Column(name = "disp_or")
	private Integer dispOr;

	@Column(name = "section_prn")
	private String sectionPrn;

	@Column(name = "work_phone")
	private String workPhone;

	@Column(name = "work_no")
	private String workNo;

	private String qq;

	private String wechat;

	@Column(name = "person_type")
	private String personType;

	private String sex;

	@Column(name = "id_num")
	private String idNum;

	private String duty;

	private String birthday;

	private String nationality;

	private String nation;

	@Column(name = "org_prn")
	private String orgPrn;

	private String education;

	private String title;

	@Column(name = "signature_url")
	private String signatureUrl;

	private String resume;

	@Column(name = "ogin_name")
	private String oginName;

	@Column(name = "up_ver")
	private Integer upVer;

	@Column(name = "manager_prns")
	private String managerPrns;

	@Column(name = "photo_url")
	private String photoUrl;

	@Column(name = "graduate_school")
	private String graduateSchool;

	@Column(name = "contact_addr")
	private String contactAddr;

	@Column(name = "zip_code")
	private String zipCode;

	@Column(name = "user_code")
	private String userCode;

	@Column(name = "home_addr")
	private String homeAddr;

	@Column(name = "home_zip")
	private String homeZip;

	private String ding;

	private String remark;

	private String major;

	private String province;

	private String city;

	private String area;

	private String pinyin;

	@Column(name = "parent_section_prn")
	private String parentSectionPrn;

	@Column(name = "parent_section_name")
	private String parentSectionName;

	@Column(name = "manager_section_name")
	private String managerSectionName;

	@Column(name = "section_name")
	private String sectionName;

	@Column(name = "org_name")
	private String orgName;

	@Column(name = "work_life")
	private Integer workLife;

	@Column(name = "work_time")
	private LocalDateTime workTime;

	private String station;

	@Column(name = "job_content")
	private String jobContent;

	@Column(name = "province_prn")
	private String provincePrn;

	@Column(name = "city_prn")
	private String cityPrn;

	@Column(name = "county_prn")
	private String countyPrn;

	@Column(name = "inspection_ability")
	private String inspectionAbility;

	@Column(name = "owned_portal")
	private String ownedPortal;

	@Column(name = "menu_color")
	private String menuColor;

}

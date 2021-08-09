package com.qzsoft.tah.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * @author aq
 * @version 1.0 2021/6/1
 */
@Entity
@Table(name = "ywpz_user_s")
@Data
public class YwpzUserSBjEntity {

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "prn")
    private String prn;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_type")
    private String userType;
    @Column(name = "user_role")
    private String userRole;
    @Column(name = "login_name")
    private String loginName;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile_phone")
    private String mobilePhone;
    @Column(name = "st")
    private String st;
    @JsonFormat(timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;
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
    @Column(name = "qq")
    private String qq;
    @Column(name = "wechat")
    private String wechat;
    @Column(name = "person_type")
    private String personType;
    @Column(name = "sex")
    private String sex;
    @Column(name = "id_num")
    private String idNum;
    @Column(name = "duty")
    private String duty;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "nation")
    private String nation;
    @Column(name = "org_prn")
    private String orgPrn;
    @Column(name = "education")
    private String education;
    @Column(name = "title")
    private String title;
    @Column(name = "signature_url")
    private String signatureUrl;
    @Column(name = "resume")
    private String resume;
    @Column(name = "ogin_name")
    private String oginName;
    @Column(name = "up_ver")
    private int upVer;
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
    @Column(name = "ding")
    private String ding;
    @Column(name = "remark")
    private String remark;
    @Column(name = "major")
    private String major;
    @Column(name = "province")
    private String province;
    @Column(name = "city")
    private String city;
    @Column(name = "area")
    private String area;
    @Column(name = "pinyin")
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
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "work_time")
    private Date workTime;
    @Column(name = "station")
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
    @Column(name = "line_manager_lname")
    private String lineManagerLname;
    @Column(name = "parent_section_xzname")
    private String parentSectionXzname;
    @Column(name = "parent_section_xzprn")
    private String parentSectionXzprn;

}

package com.three.commonclient.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限标识
 *
 */
@Data
public class SysAuthority implements Serializable {

	private static final long serialVersionUID = 280565233032255804L;

	private Long id;
	private String authorityUrl;
	private String authorityName;
	private Integer authorityType;
	private Date createDate;
	private Date updateDate;

}

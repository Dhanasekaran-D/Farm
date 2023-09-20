package com.app.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String fullName;
	private String userName;
	private String password;
	private String changePassword;
	//private UserType userType;
	private String phoneNo;
	private String email;
	private String address;
	private UUID userRoleId;		
}

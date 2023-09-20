package com.app.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;



import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tb_poultry")
public class PoultryEntity extends RecordModifier implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "poultry_name")
	private String poultryName;
	
	@Column(name = "phone_no")
	private String phoneNo;
	
	@Column(name = "address")
	private String address;
	
	@Type(type = "uuid-char")
	@Column(name = "district_id")
	private UUID districtId;
	
	@Type(type = "uuid-char")
	@Column(name = "state_id")
	private UUID stateId;
	
	@Type(type = "uuid-char")
	@Column(name = "country_id")
	private UUID countryId;


}

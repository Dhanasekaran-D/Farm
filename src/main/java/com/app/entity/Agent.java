package com.app.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_agent")
public class Agent extends RecordModifier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "agent_name")
	private String agentName;

	@Column(name = "email")
	private String emailId;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "address")
	private String address;

	@Type(type = "uuid-char")
	@Column(name = "district_id")
	private UUID districtId;

	@Type(type = "uuid-char")
	@Column(name = "poultry_id")
    private UUID poultryId;
	
	@Type(type = "uuid-char")
	@Column(name = "state_id")
	private UUID stateId;
	
	@Type(type = "uuid-char")
	@Column(name = "country_id")
	private UUID countryId;
	

}

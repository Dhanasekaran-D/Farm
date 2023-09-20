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

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "tb_customer")
public class Customer extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "name")
	private String name;

	@Column(name = "address_line")
	private String addressLine;

	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "email")
	private String email;

    @Column(name = "phone_number")
	private String phoneNumber;

	
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

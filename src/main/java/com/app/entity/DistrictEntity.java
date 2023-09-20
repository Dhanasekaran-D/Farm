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
@Table(name = "tb_district")
public class DistrictEntity extends RecordModifier implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "district_name")
	private String districtName;

	@Column(name = "short_name")
	private String shortName;


	@Type(type = "uuid-char")
	@Column(name = "state_id")
	private UUID stateId;

	@Type(type = "uuid-char")
	@Column(name = "country_id")
	private UUID countryId;

//	public static Object builder() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public void setCountryObj(Object setCountryId) {
//		// TODO Auto-generated method stub
//		
//	}
}
